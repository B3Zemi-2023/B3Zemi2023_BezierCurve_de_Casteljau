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
        // 手順2: 制御点4つのときに限定して計算を考えてみましょう(手順1に式を加えるだけです).
        // 例: Point2D p = internal(点1, 点2, _t)

        // 手順3: 列挙した式をwhileやforなどのループで圧縮しましょう.
        /* 例: for(何回ループするかは考えてください){
                  (リスト).add(計算した内分点)        // リストは自分で宣言しましょう. 作り方によってはリストが2つ必要かも？
              }
        */

        if(m_controlPoints.size() == 1){
            return m_controlPoints.get(0);
        }

        else {
            List<Point2D> evaluatePoints = new ArrayList<>();
            for (int i = 0; i <= m_controlPoints.size() - 1; i++) {
                evaluatePoints.add(internal(m_controlPoints.get(i), m_controlPoints.get(i + 1), _t));
            }

            BezierCurve newBezierCurve = new BezierCurve(evaluatePoints);
            return newBezierCurve.evaluate(_t);

        }
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
        double x = 0.0;
        /* 内分する式(x)をここに書き込んでください*/
        x = _p1.getX() * _t + _p2.getX() * (1 - _t);

        double y = 0.0;
        /* 内分する式(y)をここに書き込んでください*/
        y = _p1.getY() * _t + _p2.getY() * (1 - _t);

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


