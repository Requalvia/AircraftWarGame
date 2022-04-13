package edu.hitsz.application;

import edu.hitsz.DAO.Daolmpl;
import edu.hitsz.DAO.GameRecord;
import edu.hitsz.DAO.RecordDao;
import edu.hitsz.aircraft.*;
import edu.hitsz.basic.BackUps;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.factory.*;
import edu.hitsz.item.CureSupply;
import edu.hitsz.item.FlyingItem;
import edu.hitsz.shootStrategies.NormalShoot;
import edu.hitsz.shootStrategies.ShotgunShoot;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;


/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public class Game extends JPanel {

    private int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<AbstractAircraft> enemyAircrafts;
    private final List<AbstractBullet> heroBullets;
    private final List<AbstractBullet> enemyBullets;

    private final List<FlyingItem> flyingItems;

    private int enemyMaxNumber = 5;

    private boolean gameOverFlag = false;
    private int score = 0;
    private int time = 0;
    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    private int cycleDuration = 600;
    private int cycleTime = 0;

    //什么时间得到了火力增加道具
    private int whenGetFireSupply=-10001;
    private boolean ifFireUp=false;

    //达到多少分 就要出现boss了
    private int nextScoreAppearBoss=BackUps.SCORE_INTERVAL;
    private boolean bossTime=false;

    //boss的射击方式
    private int lastTimeBossChangeSS=-1;//SS=ShootStrategy


    public double typeOfEnemy=0;

    //enemy-factories
    private MobEnemyFactory mobEnemyFactory;
    private EliteEnemyFactory eliteEnemyFactory;
    private EnemyBossFactory enemyBossFactory;
    private BombSupplyFactory bombSupplyFactory;
    private CureSupplyFactory cureSupplyFactory;
    private FireSupplyFactory fireSupplyFactory;

    private RecordDao dao;

    public Game() {
        heroAircraft = HeroAircraft.GetInstance();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        flyingItems=new LinkedList<>();

        //初始化工厂
        mobEnemyFactory=new MobEnemyFactory();
        eliteEnemyFactory=new EliteEnemyFactory();
        enemyBossFactory=new EnemyBossFactory();
        bombSupplyFactory=new BombSupplyFactory();
        cureSupplyFactory=new CureSupplyFactory();
        fireSupplyFactory=new FireSupplyFactory();


        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

        dao=new Daolmpl();

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;

            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);

                // 新敌机产生 这里是随机普通敌机与精英敌机
                //typePfEnemy的取值范围是[0,1)，我们规定在[0,0.8)时产生普通敌机，在[0.8,1)产生精英敌机


                if(score >= nextScoreAppearBoss){
                    //如果到达boss出现的分数
                    if(enemyAircrafts.size()!=0){
                    }
                    else if(enemyAircrafts.size()==0 && (!bossTime)){
                        //直到屏幕上没有飞机了,且还没进入boss阶段
                        enemyAircrafts.add(enemyBossFactory.Create());
                        bossTime=true;
                        lastTimeBossChangeSS=time;
                    }
                    else{
                        bossTime=false;
                        nextScoreAppearBoss+=BackUps.SCORE_INTERVAL;
                    }

                }
                else{
                    //如果没有到达boss出现的分数
                    if (enemyAircrafts.size() < enemyMaxNumber) {
                        typeOfEnemy=(double) Math.random();
                        if(typeOfEnemy>=0 && typeOfEnemy<0.8){
                            enemyAircrafts.add( mobEnemyFactory.Create());
                        }
                        else if(typeOfEnemy>=0.8 && typeOfEnemy<1) {
                            enemyAircrafts.add(( eliteEnemyFactory.Create()));
                        }
                    }
                }





                // 飞机射出子弹
                shootAction();
            }

            //飞机火力增加判定
            checkFireUpTime();

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            //道具移动
            itemsMoveAction();

            // 撞击检测
            crashCheckAction();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();
            // 游戏结束检查
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
                executorService.shutdown();
                gameOverFlag = true;
            }

            if(gameOverFlag){
                String playerName=new String("Me");


                SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date=new Date();
                GameRecord gameRecord=new GameRecord(date,playerName,getScore(),dao.getAll().size());
                dao.Add(gameRecord);
                dao.printAll();
            }

        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    //***********************
    //      Action 各部分
    //***********************

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    public static boolean ifShoot=false;//精英敌机射击间隔为英雄机的一半 不然太难了



    private void checkFireUpTime(){

        if(whenGetFireSupply<=time && time<=whenGetFireSupply+ BackUps.FIRETIME){
            //如果在火力增加时间内
            ifFireUp=true;

            if(heroAircraft.getShootStrategy() instanceof NormalShoot){
                //如果不是散射模式 就改成散射模式
                heroAircraft.setShootStrategy(new ShotgunShoot());
            }
        }
        else{
            //如果不在时间内 改回来
            heroAircraft.fireDown();
            heroAircraft.setShootStrategy(new NormalShoot());
            ifFireUp=false;
        }

    }

    private void shootAction() {
        // TODO 敌机射击
        if(ifShoot){
            for(AbstractAircraft enemies:enemyAircrafts){

                if(enemies.getClass().toString().equals("class edu.hitsz.aircraft.EliteEnemy")){

                    enemyBullets.addAll(enemies.shoot());
                }
                else if(enemies.getClass().toString().equals("class edu.hitsz.aircraft.MobEnemy")){}
                else if(enemies.getClass().toString().equals("class edu.hitsz.aircraft.EnemyBoss")){
                    //是否改变策略？
                    if(lastTimeBossChangeSS+BackUps.BOSS_CHANGE<=time){
                        ((EnemyBoss)enemies).changeBSS();
                        //System.out.println("Change!");
                        lastTimeBossChangeSS=time;
                    }
                    enemyBullets.addAll(enemies.shoot());
                }
            }
        }
        ifShoot=(!ifShoot);


        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
    }

    private void bulletsMoveAction() {
        for (AbstractBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (AbstractBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }
    private void itemsMoveAction() {
        for (FlyingItem fi : flyingItems) {
            fi.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // TODO 敌机子弹攻击英雄

        for(AbstractBullet bullet : enemyBullets){
            if (bullet.notValid()) {continue;}
            if(heroAircraft.crash(bullet)){
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }

        }

        // 英雄子弹攻击敌机
        for (AbstractBullet bullet : heroBullets) {
            
            if (bullet.notValid()) {
                continue;
            }

            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // TODO 获得分数，产生道具补给
                        if(enemyAircraft.getClass().toString().equals("class edu.hitsz.aircraft.EliteEnemy")){
                            score += BackUps.SCORE_ELITEENEMY;
                            //精英机被摧毁时 有0.75几率产生三种道具之一
                            flyingItems.addAll(EliteEnemy.generateItemUponDeath(
                                    enemyAircraft.getLocationX()*1,
                                    enemyAircraft.getLocationY()*1,
                                    bombSupplyFactory,
                                    cureSupplyFactory,
                                    fireSupplyFactory));
                        }
                        else if(enemyAircraft.getClass().toString().equals("class edu.hitsz.aircraft.MobEnemy")){
                            score += BackUps.SCORE_MOBENEMY;
                        }
                        else if(enemyAircraft.getClass().toString().equals("class edu.hitsz.aircraft.EnemyBoss")){
                            score += BackUps.SCORE_BOSS;
                        }
                    }
                }

                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }

            }

        }
        // Todo: 我方获得道具，道具生效
        for(FlyingItem fi:flyingItems){

            if(fi.notValid()){continue;}
            else{

                if(fi.crash(heroAircraft)){
                    if(fi.getType().equals("CureSupply")){
                        heroAircraft.getCure(((CureSupply)fi).getCureAmount());
                    }
                    else if(fi.getType().equals("BombSupply")){
                        System.out.println("BombSupply Active");
                    }
                    else if(fi.getType().equals("FireSupply")){
                        whenGetFireSupply=time;
                        System.out.println("FireSupply Active"+whenGetFireSupply);
                        heroAircraft.fireUp(1);
                    }
                    fi.vanish();
                }
            }


        }

    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 检查英雄机生存
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        flyingItems.removeIf(FlyingItem::notValid);
    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param  g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        paintImageWithPositionRevised(g, flyingItems);
        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);

        paintImageWithPositionRevised(g, enemyAircrafts);


        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }

    public boolean isGameOverFlag() {
        return gameOverFlag;
    }
    public int getScore(){
        return score;
    }
}
