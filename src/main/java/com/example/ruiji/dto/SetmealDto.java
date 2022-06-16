package com.example.ruiji.dto;

import com.example.ruiji.entity.Setmeal;
import com.example.ruiji.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
