package android.luna.ViewUi.DragUi;

import android.graphics.Point;
import android.view.View;

/**
 * Created by lee on 2016/8/26.
 */
public class DetailView {
    private Point mBeginPoint;
    private int mWidthNum;
    private int mHeightNum;
    private View mView;
    private int idbysort;
    public int getIdSort() {
		return idbysort;
	}

	public void setIdSort(int idbysort) {
		this.idbysort = idbysort;
	}
	
	private int pid;

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public DetailView() {
        this(null, 0, 0, null);
    }

    public DetailView(DetailView detailView) {
        setPoint(detailView.getPoint());
        setWidhtNum(detailView.getWidthNum());
        setHeightNum(detailView.getHeightNum());
        setView(detailView.getView());
    }

    public DetailView(Point p, int width, int height, View v, int idbysort, int pid) {
        setPoint(p);
        setWidhtNum(width);
        setHeightNum(height);
        setView(v);
        setIdSort(idbysort);
        setPid(pid);
    }

    public DetailView(Point p, int width, int height, View v) {
        setPoint(p);
        setWidhtNum(width);
        setHeightNum(height);
        setView(v);
    }
    public boolean bSameSize(DetailView v) {
        return (mWidthNum == v.getWidthNum() && mHeightNum == v.getHeightNum());
    }

    public void setPoint(Point p) {
        mBeginPoint = p;
    }

    public Point getPoint() {
        return mBeginPoint;
    }

    public void setWidhtNum(int i) {
        mWidthNum = i;
    }

    public int getWidthNum() {
        return mWidthNum;
    }

    public void setHeightNum(int i) {
        mHeightNum = i;
    }

    public int getHeightNum() {
        return mHeightNum;
    }

    public void setView(View v) {
        mView = v;
    }

    public View getView() {
        return mView;
    }
    
    public void copy(DetailView cpy)
    {
    	this.mView = cpy.getView();
    	this.idbysort =cpy.getIdSort();
    	this.mHeightNum = cpy.getHeightNum();
    	this.pid = cpy.getPid();
    	this.mWidthNum =cpy.getWidthNum();
    	this.mBeginPoint = new Point(cpy.getPoint().x,cpy.getPoint().y);
    }
}
