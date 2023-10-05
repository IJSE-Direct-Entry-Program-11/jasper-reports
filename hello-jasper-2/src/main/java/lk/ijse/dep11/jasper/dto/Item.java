package lk.ijse.dep11.jasper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item implements Serializable {
    private String code;            // $F{code}
    private String description;     // $F{description}
    private int qty;                // $F{qty}
    private BigDecimal price;       // $F{price}
}
