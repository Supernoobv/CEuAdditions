/*package CEuAdditions.common.item;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.metaitem.StandardMetaItem;
import gregtech.api.items.metaitem.stats.IItemContainerItemProvider;
import gregtech.api.items.toolitem.ToolMetaItem;
import net.minecraft.item.Item;

public class CEuAMetaItem extends StandardMetaItem {
    public static MetaItem<?>.MetaValueItem AIR_FILTER;

    @Override
    public void registerSubItems() {
        IItemContainerItemProvider selfContainerItemProvider = itemStack -> itemStack;

        AIR_FILTER = addItem(1, "air.filter");
        Item air_filter = AIR_FILTER.getMetaItem();
        air_filter.setMaxDamage(50000);
    }
}


 */