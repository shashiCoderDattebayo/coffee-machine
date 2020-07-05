package com.shashi.coffeemachine.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Configuration {
    private final Machine machine;

    @JsonCreator
    public Configuration(@JsonProperty("machine") Machine machine) {
        this.machine = machine;
    }
}
