package com.omtou.ruiji.dto;

import com.omtou.ruiji.entity.Setmeal;
import com.omtou.ruiji.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
