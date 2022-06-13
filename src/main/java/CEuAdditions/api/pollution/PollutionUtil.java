package CEuAdditions.api.pollution;

import CEuAdditions.api.ConfigHandler;
import gregtech.common.items.MetaItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.Potion;

import java.util.HashMap;

public class PollutionUtil {

    public HashMap<String, Integer> filterList = new HashMap<String, Integer>();

    public static Potion blindness = Potion.getPotionById(15);
    public static Potion nausea = Potion.getPotionById(9);
    public static Potion hunger = Potion.getPotionById(17);
    public static Potion poison = Potion.getPotionById(19);
    public static Potion slowness = Potion.getPotionById(2);
    public static Potion weakness = Potion.getPotionById(18);
    public static Potion fatigue = Potion.getPotionById(4);

    public boolean checkIfPlayerHasNano(EntityLivingBase player) {
        if (!player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).isItemEqual(MetaItems.NANO_HELMET.getStackForm())) return false;
        if (!player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).isItemEqual(MetaItems.NANO_CHESTPLATE.getStackForm())  || (!player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).isItemEqual(MetaItems.NANO_CHESTPLATE_ADVANCED.getStackForm()))) return false;
        if (!player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).isItemEqual(MetaItems.NANO_LEGGINGS.getStackForm())) return false;
        if (!player.getItemStackFromSlot(EntityEquipmentSlot.FEET).isItemEqual(MetaItems.NANO_BOOTS .getStackForm())) return false;

        return true;
    }
    public boolean checkIfPlayerHasQuarkTech(EntityLivingBase player) {
        if (!player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).isItemEqual(MetaItems.QUANTUM_HELMET.getStackForm())) return false;
        if (!player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).isItemEqual(MetaItems.QUANTUM_CHESTPLATE.getStackForm())  || (!player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).isItemEqual(MetaItems.QUANTUM_CHESTPLATE_ADVANCED.getStackForm()))) return false;
        if (!player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).isItemEqual(MetaItems.QUANTUM_LEGGINGS.getStackForm())) return false;
        if (!player.getItemStackFromSlot(EntityEquipmentSlot.FEET).isItemEqual(MetaItems.QUANTUM_BOOTS .getStackForm())) return false;

        return true;
    }

    public void initFilterList() {

        // Optimization needed
        filterList.put("gregtech.machine.steam_boiler_coal_bronze", ConfigHandler.polesOptions.BronzeBoilerEmissionRate);
        filterList.put("gregtech.machine.steam_boiler_lava_bronze", ConfigHandler.polesOptions.BronzeBoilerEmissionRate);
        filterList.put("gregtech.machine.steam_furnace_bronze", ConfigHandler.polesOptions.BronzeBoilerEmissionRate);
        filterList.put("gregtech.machine.steam_boiler_lava_steel", ConfigHandler.polesOptions.SteelBoilerEmissionRate);
        filterList.put("gregtech.machine.steam_boiler_coal_steel", ConfigHandler.polesOptions.SteelBoilerEmissionRate);
        filterList.put("gregtech.machine.steam_furnace_steel", ConfigHandler.polesOptions.SteelBoilerEmissionRate);
        filterList.put("gregtech.machine.combustion_generator.lv", ConfigHandler.polesOptions.CombustionEmissionRate);
        filterList.put("gregtech.machine.combustion_generator.mv", ConfigHandler.polesOptions.CombustionEmissionRate * 2);
        filterList.put("gregtech.machine.combustion_generator.hv", ConfigHandler.polesOptions.CombustionEmissionRate * 4);
        filterList.put("gregtech.machine.gas_turbine.lv", ConfigHandler.polesOptions.GasTurbineEmissionRate);
        filterList.put("gregtech.machine.gas_turbine.mv", ConfigHandler.polesOptions.GasTurbineEmissionRate * 2);
        filterList.put("gregtech.machine.gas_turbine.hv", ConfigHandler.polesOptions.GasTurbineEmissionRate * 4);
        filterList.put("gregtech.machine.primitive_blast_furnace.bronze", ConfigHandler.polesOptions.PBFEmissionRate);
        filterList.put("gregtech.machine.electric_blast_furnace", ConfigHandler.polesOptions.EBFEmissionRate);
        filterList.put("gregtech.machine.implosion_compressor", ConfigHandler.polesOptions.ImplosionCompressorEmissionRate);
        filterList.put("gregtech.machine.pyrolyse_oven", ConfigHandler.polesOptions.PyrolyseOvenEmissionRate);
        filterList.put("gregtech.machine.multi_furnace", ConfigHandler.polesOptions.MultiSmelterEmissionRate);
        filterList.put("gregtech.machine.large_combustion_engine", ConfigHandler.polesOptions.LargeCombustionEmissionRate);
        filterList.put("gregtech.machine.extreme_combustion_engine", ConfigHandler.polesOptions.LargeCombustionEmissionRate * 2);
        filterList.put("gregtech.machine.large_turbine.gas", ConfigHandler.polesOptions.LargeGasTurbineEmissionRate);
        filterList.put("gregtech.machine.large_boiler.bronze", ConfigHandler.polesOptions.LargeBoilerEmissionRate);
        filterList.put("gregtech.machine.large_boiler.steel", ConfigHandler.polesOptions.LargeBoilerEmissionRate * 2);
        filterList.put("gregtech.machine.large_boiler.titanium", ConfigHandler.polesOptions.LargeBoilerEmissionRate * 4);
        filterList.put("gregtech.machine.large_boiler.tungstensteel", ConfigHandler.polesOptions.LargeBoilerEmissionRate * 6);


    }

}
