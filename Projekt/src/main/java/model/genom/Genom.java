package model.genom;

public interface Genom {
    /**
     * Function which returns the gen on next or random position (it depends on the type of genom).
     *
     * @return gen on proper position.
     */
    int getGen();

    /**
     * Function which generates exactly one gen.
     *
     * @return new gen.
     */
    char generateGen();

    /**
     * Function which generates new gen to swap it with the specified gen which is supposed to be muted.
     * Of course, it will not generate the same gen.
     *
     * @return new, muted gen.
     */
    char generateMutedGen(char genToBeMuted);
}
