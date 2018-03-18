package com.keyeswest.bake_v2.dependInjection;



import com.keyeswest.bake_v2.BakeApplication;


public class DependencyInjector {

    private static BakeComponent bakeComponent;

    public static void initialize(BakeApplication bakeApplication) {
        bakeComponent = DaggerBakeComponent.builder()
                .bakeModule(new BakeModule(bakeApplication))
                .build();
    }

    public static BakeComponent bakeComponent() {
        return bakeComponent;
    }

    private DependencyInjector(){}

}
