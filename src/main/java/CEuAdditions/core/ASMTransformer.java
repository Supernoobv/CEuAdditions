package CEuAdditions.core;

import net.minecraft.launchwrapper.IClassTransformer;

public class ASMTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        return new byte[0];
    }

    // TODO INCASE I NEED TO USE ASM!
}
