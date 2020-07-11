package com.shashi.coffeemachine.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AppConfiguration {
    private final MachineConfiguration machineConfiguration;

    @JsonCreator
    public AppConfiguration(@JsonProperty("machine") MachineConfiguration machineConfiguration) {
        this.machineConfiguration = machineConfiguration;
    }
}
