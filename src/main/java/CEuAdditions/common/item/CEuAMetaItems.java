/* package CEuAdditions.common.item;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.common.items.MetaTool;

import java.util.List;

public class CEuAMetaItems {
    public static List<MetaItem<?>> ITEMS = MetaItem.getMetaItems();
    public static CEuAMetaItem META_ITEM;

    public static MetaTool metatool;
    public static void init() {
        META_ITEM = new CEuAMetaItem();
        META_ITEM.setRegistryName("CEuA_meta_item");

        CEuAMetaItems.metatool = new MetaTool() {
            @Override
            public void registerSubItems() {
            }
        };
        metatool.setRegistryName("CEuA_meta_tool");
    }
}


 */