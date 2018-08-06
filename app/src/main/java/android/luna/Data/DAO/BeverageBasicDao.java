package android.luna.Data.DAO;

import android.content.Context;
import android.luna.Activity.Base.CremApp;
import android.luna.Data.module.DrinkName;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.Locale;

public class BeverageBasicDao extends BaseDaobak {
	public BeverageBasicDao(Context context, CremApp app) {
		super(context,app);
	}

}
