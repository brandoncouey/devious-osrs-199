package io.ruin.model.merchant;

import io.ruin.model.item.Item;
import lombok.Getter;
import lombok.Setter;

public class MerchantItem extends Item {

    @Getter @Setter
    private int stock;

    public MerchantItem(int id, int stock) {
        super(id, stock);
    }


}
