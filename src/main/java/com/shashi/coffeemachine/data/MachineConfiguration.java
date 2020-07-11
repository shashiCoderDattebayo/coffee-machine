package com.shashi.coffeemachine.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.apache.commons.collections4.MapUtils;

import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

@Getter
public class MachineConfiguration {
    private final int outlets;
    private final Map<String, Integer> ingredientsStock;
    private final Map<String, Map<String, Integer>> beverages;

    @JsonCreator
    public MachineConfiguration(@JsonProperty("outlets") Map<String, Integer> outlets,
                                @JsonProperty("total_items_quantity") Map<String, Integer> ingredientsStock,
                                @JsonProperty("beverages") Map<String, Map<String, Integer>> beverages) {
        validateIngredients(ingredientsStock, beverages);
        this.outlets = validateAndGetOutlets(outlets);
        this.ingredientsStock = ingredientsStock;
        this.beverages = beverages;
    }

    private void validateIngredients(Map<String, Integer> totalItemsQuantity, Map<String, Map<String, Integer>> beverages) {
        // TODO: 05/07/20 Add Validations
    }

    private int validateAndGetOutlets(Map<String, Integer> outlets) {
        checkArgument(MapUtils.isNotEmpty(outlets), "outlets cannot be empty");
        checkArgument(outlets.containsKey("count_n"), "count_n should be present in outlets");
        return outlets.get("count_n");
    }
}
