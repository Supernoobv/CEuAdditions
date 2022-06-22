package ceuadditions.api.capability;

public interface IHugeFan {
    /**
     * @return true whether the front face is free.
     */
    boolean isFrontFaceFree();

    /**
     *
     * @return true whether the fan is in a formed multiblock.
     */
    boolean getFan();


    /**
     *
     * @return true whether the multiblock is formed and active
     */
    boolean getActiveFan();

    /**
     *
     * @return the tier of the fan.
     */
    int getFanTier();
}
