package jp.sagalab.b3zemi;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class BezierCurve {


    /**
     * Bezier曲線の生成を行うためのstaticファクトリーメソッドです.
     * @param _controlPoints 制御点列
     * @return Bezier曲線のインスタンス
     */
    public static BezierCurve create (List<Point2D> _controlPoints){
        return new BezierCurve(_controlPoints);
    }

    /**
     * パラメータ t に対応する評価点を De Casteljau のアルゴリズムで評価する.
     * @param _t 閉区間 [ 0, 1 ] 内のパラメータ
     * @return パラメータ t に対応する評価点
     */
    public Point2D evaluate(double _t){
        List<Point2D> copyPoints = new ArrayList<>(m_controlPoints);
        ArrayList<Point2D> bezierPoints = new ArrayList<>();
        while (copyPoints.size() > 1){
            bezierPoints.clear();
            for(int j = 0; j < copyPoints.size() - 1; j++){
                double x = (1.0 - _t) * copyPoints.get(j).getX() + _t * copyPoints.get(j+1).getX();
                double y = (1.0 - _t) * copyPoints.get(j).getY() + _t * copyPoints.get(j+1).getY();
                bezierPoints.add(new Point2D.Double(x, y));
            }
            copyPoints = new ArrayList<>(bezierPoints);
        }
        return bezierPoints.get(0);
    }

    /**
     * 制御点列のコピーを取得する.
     * @return 制御点列のコピー
     */
    public List<Point2D> getControlPoints(){
        return m_controlPoints;
    }

    /**
     * 次数を取得する.
     * @return 次数
     */
    public int getDegree(){
        return m_controlPoints.size() - 1;
    }

    /**
     * 制御点列を指定してBezier曲線オブジェクトを生成する.
     * @param _controlPoints 制御点列
     */
    private BezierCurve(List<Point2D> _controlPoints){
        m_controlPoints = _controlPoints;
    }

    /** 制御点列 */
    private final List<Point2D> m_controlPoints;
}
