package jp.sagalab.b3zemi;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class BezierCurve {

    /** 制御点列 */
    private final List<Point2D> m_controlPoints;

    /**
     * パラメータ t に対応する評価点を De Casteljau のアルゴリズムで評価する.
     * @param _t 閉区間 [ 0, 1 ] 内のパラメータ
     * @return パラメータ t に対応する評価点
     */
    public Point2D evaluate(double _t){
        //手順は1例です. 分かる人は無視して大丈夫です.
        // 手順1: 制御点3つのときに限定して計算を考えてみましょう(式を列挙する形でも〇)
//        temppoint.clear();
//        double x11 = (1 - _t) * m_controlPoints.get(0).getX() + _t * m_controlPoints.get(1).getX();
//        double y11 = (1 - _t) * m_controlPoints.get(0).getY() + _t * m_controlPoints.get(1).getY();
//        Point2D.Double p1 = new Point2D.Double(x11, y11);
//        double x12 = (1 - _t) * m_controlPoints.get(1).getX() + _t * m_controlPoints.get(2).getX();
//        double y12 = (1 - _t) * m_controlPoints.get(1).getY() + _t * m_controlPoints.get(2).getY();
//        Point2D.Double p2 = new Point2D.Double(x12, y12);
//        temppoint.add(p1);
//        temppoint.add(p2);
//        double x21 = (1 - _t) * temppoint.get(0).getX() + _t * temppoint.get(1).getX();
//        double y21 = (1 - _t) * temppoint.get(0).getY() + _t * temppoint.get(1).getY();
//        Point2D.Double lastpoint = new Point2D.Double(x21,y21);
//        return new Point2D.Double (lastpoint.getX(),lastpoint.getY());
        // 手順2: 制御点4つのときに限定して計算を考えてみましょう(手順1に式を加えるだけです).
//        double x11 = (1 - _t) * m_controlPoints.get(0).getX() + _t * m_controlPoints.get(1).getX();
//        double y11 = (1 - _t) * m_controlPoints.get(0).getY() + _t * m_controlPoints.get(1).getY();
//        Point2D.Double p1 = new Point2D.Double(x11, y11);
//        double x12 = (1 - _t) * m_controlPoints.get(1).getX() + _t * m_controlPoints.get(2).getX();
//        double y12 = (1 - _t) * m_controlPoints.get(1).getY() + _t * m_controlPoints.get(2).getY();
//        Point2D.Double p2 = new Point2D.Double(x12, y12);
//        double x13 = (1 - _t) * m_controlPoints.get(2).getX() + _t * m_controlPoints.get(3).getX();
//        double y13 = (1 - _t) * m_controlPoints.get(2).getY() + _t * m_controlPoints.get(3).getY();
//        Point2D.Double p3 = new Point2D.Double(x13, y13);
//
//        double x21 = (1 - _t) * p1.getX() + _t * p2.getX();
//        double y21 = (1 - _t) * p1.getY() + _t * p2.getY();
//        Point2D.Double p4 = new Point2D.Double(x21, y21);
//        double x22 = (1 - _t) * p2.getX() + _t * p3.getX();
//        double y22 = (1 - _t) * p2.getY() + _t * p3.getY();
//        Point2D.Double p5 = new Point2D.Double(x22, y22);
//
//        double x31 = (1 - _t) * p4.getX() + _t * p5.getX();
//        double y31 = (1 - _t) * p4.getY() + _t * p5.getY();
//        Point2D.Double lastpoint = new Point2D.Double(x31,y31);
//        return new Point2D.Double (lastpoint.getX(),lastpoint.getY());
        // 例: Point2D p = internal(点1, 点2, _t)

        // 手順3: 列挙した式をwhileやforなどのループで圧縮しましょう.
        /* 制御点をコピーしたリストを用意. */
        /* 計算結果を保存するリストを用意. */


        while (){
            temppoint.clear();
            /* 保存用リストを初期化. */
            for(int i=0;i<m_controlPoints.size()-1;i++){
                /* 計算して保存用リストに追加. */
                temppoint.add(internal(m_controlPoints.get(i),m_controlPoints.get(i+1),_t));
            }
            /* コピーリストに保存用リストをコピーする */ //注意 listA = listBの形ではありません.

        }
        return /* コピーの一つ目を返す. */;

        /* 例: for(何回ループするかは考えてください){
                  (リスト).add(計算した内分点)        // リストは自分で宣言しましょう. 作り方によってはリストが2つ必要かも？
              }
        */
//        return null;
    }

    /**
     * 2点を内分する点を計算する.
     * @param _p1 1つめの点
     * @param _p2 2つめの点
     * @param _t 比率
     * @return 内分点
     */
    public static Point2D.Double internal(Point2D _p1, Point2D _p2, double _t){
        //_p1と_p2を(1-_t):_tに内分する.
        double x = (1 - _t)*_p1.getX() + _t*_p2.getX(); /* 内分する式(x)をここに書き込んでください*/
        double y = (1 - _t)*_p1.getY() + _t*_p2.getY(); /* 内分する式(y)をここに書き込んでください*/

        return new Point2D.Double(x, y);
    }

    public List<Point2D> getControlPoints(){

        return new ArrayList<>(m_controlPoints);
    }

    /**
     * Bezier曲線の生成を行うためのstaticファクトリーメソッドです.
     * @param _controlPoints 制御点列
     * @return Bezier曲線のインスタンス
     */
    public static BezierCurve create (List<Point2D> _controlPoints){
        return new BezierCurve(_controlPoints);
    }

    /**
     * 制御点列を指定してBezier曲線オブジェクトを生成する.
     * @param _controlPoints 制御点列
     */
    private BezierCurve(List<Point2D> _controlPoints){
        m_controlPoints = _controlPoints;
    }
    private List<Point2D> temppoint = new ArrayList<>();
    private List<Point2D> temppoint2 = new ArrayList<>();

//----------------------------------------------------------------------------------------------------------------------
//どうしてもわからないときに利用してください.
//利用する際はプログラムの意味を理解してください.

//再帰なし
//    public Point2D evaluate(double _t){
//        /* 制御点をコピーしたリストを用意. */
//        /* 計算結果を保存するリストを用意. */
//        while (/* コピーリストが0よりも大きいときループ */){
//            /* 保存用リストを初期化. */
//            for(/* コピーリストのサイズ-1までループ */){
//                /* 計算して保存用リストに追加. */
//            }
//            /* コピーリストに保存用リストをコピーする */ //注意 listA = listBの形ではありません.
//        }
//        return /* コピーの一つ目を返す. */;
//    }

//再帰あり
//    public Point2D evaluate(double _t){
//        /* 計算結果を保存するリストを用意 */
//        if(/* 制御点の数が1の時 */){
//            /* 制御点の1つ目を返す. */
//        }
//        for(/* 制御点の数-1までループ */){
//            /* 計算して保存用リストに追加. */
//        }
//        /* BezierCurveクラスのインスタンを生成*/
//        return /* 計算結果を返す. */;
//    }
}


