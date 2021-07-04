package Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class Machine {

    @JsonProperty("outlets")
    private Outlet outlets;

    @JsonProperty("total_items_quantity")
    private LinkedHashMap<String, Integer> ingredientsQuantityMap;

    @JsonProperty("beverages")
    private LinkedHashMap<String, Map<String, Integer>> beveragesMap;

}