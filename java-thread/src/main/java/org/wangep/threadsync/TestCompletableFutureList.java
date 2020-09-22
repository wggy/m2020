package org.wangep.threadsync;

import com.sun.org.apache.xerces.internal.util.MessageFormatter;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/***
 * created by wange on 2020/7/2 17:28
 */
public class TestCompletableFutureList {


    private static final List<String> IMPORTANT_TABLES = Arrays.asList("l_online_log", "l_recharge_log");

    private static final List<String> NEW_TABLES = Arrays.asList("l_activity_monster_atk_log",
            "l_reputation_pvp_task_log", "l_atk_citywall_log", "l_activity_bonfire_log",
            "l_activaty_bonfire_match_log", "l_activity_fire_buff_log", "l_minigame_reward_log",
            "l_gambling_start_log", "l_gambling_result_log", "l_activity_trade_log", "l_activity_tradebill_log",
            "l_group_member_log", "l_player_chat_log", "l_city_master_log");

    private static final List<String> UPDATED_TABLES = Arrays.asList("l_alliance_log", "l_alliance_official_log",
            "l_alliance_group_log", "l_alliance_order_log", "l_alliance_donate_log", "l_alliance_tech_log",
            "l_alliance_finance_log", "l_alliance_relation_log", "l_donate_rank_log", "l_info_release_log",
            "l_hero_recruit_log", "l_hero_shard_log", "l_hero_log", "l_hero_upgrade_log", "l_hero_reset_log",
            "l_skill_shard_log", "l_hero_skill_log", "l_build_log", "l_upgrade_log", "l_train_army_log",
            "l_upgrade_soldiers_log", "l_cure_army_log", "l_hide_army_log", "l_atk_robber_log", "l_online_reward_log"
            , "l_horses_game_log", "l_chg_culture_log", "l_gain_exp_log", "l_stamina_log", "l_vipnum_log",
            "l_talent_point_log", "l_figure_log", "l_signature_log", "l_shop_refresh_log", "l_shop_trigger_log",
            "l_shop_startend_log", "l_pay_click_log", "l_shop_purchase_log", "l_map_collection_log"
            , "l_map_search_log", "l_pm_log", "l_friend_list_log", "l_shield_list_log", "l_citywar_contract_log");


    public static void main(String[] args) throws Exception {

        String startDate = "20200623_00";
        String endDate = "20200623_01";
        String project = "woe";
        String tabs = "t";

        List<String> dateList = buildDateHHList(startDate, endDate);

        System.out.println("IMPORTANT_TABLES-----------------Starting");
        CompletableFuture<List<Void>> listFutures = repairTables(IMPORTANT_TABLES, dateList, project, tabs);

        listFutures
                .thenRun(() -> {
                    System.out.println("IMPORTANT_TABLES-----------------Finished");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("0000000000000000000");
                })
                .thenRun(() -> {
                    try {
                        repairTables(NEW_TABLES, dateList, project, tabs).get();
                        try {
                            TimeUnit.SECONDS.sleep(3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("111111111111111111111111111111111");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .thenRun(() -> {
                    System.out.println("NEW_TABLES-----------------Finished");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("2222222222222222222222222222222222");
                })
                .thenRun(() -> {
                    try {
                        for (String table : UPDATED_TABLES) {
                            repairTables(table, dateList, project, tabs).get();
                            System.out.println(table + "  AllFinished------------------");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .thenRun(() -> {
                    System.out.println("UPDATED_TABLES-----------------Finished");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("33333333333333333333333333");
                })
                .get();


//        System.out.println("NEW_TABLES-----------------Starting");
//        listFutures = repairTables(NEW_TABLES, dateList, project, tabs);
//        listFutures.get();
//        System.out.println("NEW_TABLES-----------------Finished");
//
//        System.out.println("UPDATED_TABLES-----------------Starting");
//        listFutures = repairTables(UPDATED_TABLES, dateList, project, tabs);
//        listFutures.get();
//        System.out.println("UPDATED_TABLES-----------------Finished");

    }

    private static List<String> buildDateHHList(String startDateHH, String endDateHH) {
        String sDate = startDateHH;
        List<String> dateList = new ArrayList<>();
        if (StringUtils.isEmpty(sDate)) return dateList;
        while (sDate.compareTo(endDateHH) <= 0) {
            dateList.add(sDate);
            sDate = getBeforeHour(sDate, 1);
        }
        return dateList;
    }


    private static String getBeforeHour(String dateHour, int hour) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HH");
        try {
            Date date = sdf.parse(dateHour);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);
            return sdf.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures) {
        CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        return allDoneFuture.thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList()));
    }

    private static CompletableFuture<List<Void>> repairTables(List<String> tableList, List<String> dateList, String project, String tabs) {
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (String table : tableList) {
            for (String datehh : dateList) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(() ->
                        loopDate(datehh, project, table, "hdfs2hdfs", tabs));
                futures.add(future);
            }
        }
        return sequence(futures);
    }

    private static CompletableFuture<List<Void>> repairTables(String table, List<String> dateList, String project, String tabs) {
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (String datehh : dateList) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() ->
                    loopDate(datehh, project, table, "hdfs2hdfs", tabs));
            futures.add(future);
        }
        return sequence(futures);
    }

    private static void loopDate(String datehh, String project, String table, String folder, String tabs) {
        if (table.startsWith("l_")) {
            table = table.substring(2);
        }
        String date = datehh.substring(0, 8);
        String hh = datehh.substring(9);
        String zipFileName = table.concat(hh).concat(".zip");
        String hdfsPath = "/gamelog_bak/gamelog_new_bak/"
                .concat(project)
                .concat("/")
                .concat(date)
                .concat("/")
                .concat(hh)
                .concat("/")
                .concat(zipFileName);
        System.out.println("thread: " + Thread.currentThread().getName() + ", file: " + hdfsPath);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
