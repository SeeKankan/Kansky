package io.seekankan.github.kansky.kanattribute;

import io.seekankan.github.kansky.Kansky;
import io.seekankan.github.kansky.util.KanskyUtil;
import org.bukkit.entity.LivingEntity;

import java.util.*;

public interface AttributeTracker {
    List<AttributeTracker> attributeTrackers = new ArrayList<>(2);
    LinkedList<DamageModifier> entityModifier = new LinkedList<>();
    LinkedList<DamageModifier> damagerModifier = new LinkedList<>();
//    class CalcAsync extends BukkitRunnable {
//        @Override
//        public void run() {
//            Future<List<LivingEntity>> futureEntities = Bukkit.getScheduler().callSyncMethod(Kansky.getInstance(),() -> {
//                LinkedList<LivingEntity> allEntity = new LinkedList<>();
//                Bukkit.getWorlds().forEach(world -> world.getEntities().forEach(entity -> {
//                    if(entity instanceof LivingEntity) allEntity.add((LivingEntity)entity);
//                }));
//                return allEntity;
//            });
//            try {
//                List<LivingEntity> entities = futureEntities.get();
//                Iterator<LivingEntity> it = entities.iterator();
//                while(it.hasNext()) {
//                    LivingEntity entity = it.next();
//                    Map<String,Double> modifier = trackModifierForce(entity);
//                    modifierCache.put(entity,modifier);
//                }
//            } catch (InterruptedException e) {
//                Kansky.getInstance().getLogger().severe("Calc attribute has been interrupt!");
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                Kansky.getInstance().getLogger().severe("An exception was create on calc attribute!");
//                e.printStackTrace();
//            }
//        }
//    }
//    ConcurrentHashMap<LivingEntity,Map<String,Double>> modifierCache = new ConcurrentHashMap<>();

    Map<String,Double> getModifier(LivingEntity entity);
    static Map<String,Double> trackModifier(LivingEntity entity) {
//        if(Config.doCalcAsync) {
//            Map<String,Double> modifier =  modifierCache.get(entity);
//            if(modifier == null) {
//                modifier = trackModifierForce(entity);
//                modifierCache.put(entity,modifier);
//            }
//            return modifier;
//        } else
          return trackModifierForce(entity);
    }
    static Map<String,Double> trackModifierForce(LivingEntity entity) {
        List<Map<String,Double>> modifiers = new ArrayList<>();
        attributeTrackers.forEach(tracker -> modifiers.add(tracker.getModifier(entity)));
        Map<String,Double> map = new HashMap<>();
        KanskyUtil.mergeMapByPlus(map,modifiers.toArray(new Map[0]));
        return map;
    }
//    static BukkitTask startCalcAsync() {
//        return new CalcAsync().runTaskLaterAsynchronously(Kansky.getInstance(),0);
//    }
}
