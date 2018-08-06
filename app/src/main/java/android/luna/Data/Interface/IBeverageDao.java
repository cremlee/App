package android.luna.Data.Interface;

/**
 * Created by Lee.li on 2018/2/12.
 */

public interface IBeverageDao {
     IBeverageBasic getBeverageBasicDao();
     IBeverageUi getBeverageUiDao();
     IBeverageName getBeverageNameDao();
     IBeverageIngredient getBeverageIngerdient();
     IBeverageGroup getBeverageGroup();
     IBeverageCount getBeverageCountDao();
}
