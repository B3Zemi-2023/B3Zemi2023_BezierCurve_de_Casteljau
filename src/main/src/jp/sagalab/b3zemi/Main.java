package jp.sagalab.b3zemi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {

    /**
     * @param _args the command line arguments
     */
    public static void main(String[] _args) {
        new Main();
    }

    /**
     * パネルに描画を行います．
     * @param _g  the <code>Graphics</code> context in which to paint
     */
    @Override
    public void paint(Graphics _g) {
        super.paint(_g);
        if (m_controlPoints.size() > 0){
            for (int i = 0; i < m_controlPoints.size(); i++){
                drawPoint(m_controlPoints.get(i), Color.RED, _g);
            }
        }

        if (m_evaluatePoints.size() > 0){
            for (int i = 0; i < m_evaluatePoints.size()-1; i++){
                drawLine(m_evaluatePoints.get(i), m_evaluatePoints.get(i+1), Color.blue, _g);
            }
        }
    }

    /**
     * 指定した点を指定した色でパネルに描画します．
     * @param _point 点
     * @param _color 色
     * @param _g     グラフィックス
     */
    private void drawPoint(Point2D _point, Color _color, Graphics _g) {
        double radius = POINT_RADIUS;
        _g.setColor(_color);
        Graphics2D g2D = _g instanceof Graphics2D ? ((Graphics2D) _g) : null;
        if (g2D != null) {
            g2D.setStroke(new BasicStroke(STROKE_WIDTH));
            g2D.draw(new Ellipse2D.Double(_point.getX() - radius, _point.getY() - radius, radius * 2, radius * 2));
        } else {
            _g.drawOval((int) (_point.getX() - radius), (int) (_point.getY() - radius), (int) (radius * 2), (int) (radius * 2));
        }
    }

    /**
     * 指定した２つの点の座標を結ぶ線分を指定した色で描画します．
     * @param _p1    1つ目の点
     * @param _p2    ２つ目の点
     * @param _color 色
     * @param _g     グラフィックス
     */
    private void drawLine(Point2D _p1, Point2D _p2, Color _color, Graphics _g) {
        _g.setColor(_color);
        Graphics2D g2D = _g instanceof Graphics2D ? ((Graphics2D) _g) : null;
        if (g2D != null) {
            g2D.setStroke(new BasicStroke(STROKE_WIDTH));
            g2D.draw(new Line2D.Double(_p1.getX(), _p1.getY(), _p2.getX(), _p2.getY()));
        } else {
            _g.drawLine((int) _p1.getX(), (int) _p1.getY(), (int) _p2.getX(), (int) _p2.getY());
        }
    }

    /**
     * 制御点列からBezierCurveのインスタンスを生成し，評価点列を求めます.
     */
    private void calculate() {
        BezierCurve bezierCurve = BezierCurve.create(m_controlPoints);
        List<Point2D> evaluatePoints = new ArrayList<>();

        for(double t = 0; t < 1.0; t += 0.01){
            m_evaluatePoints.add(bezierCurve.evaluate(t));
        }
    }

    /**
     * コンストラクタ
     */
    public Main() {
        setTitle("Drawer");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                m_controlPoints.add(new Point2D.Double(e.getX(),e.getY()));

                if(m_controlPoints.size() > 2){
                    calculate();
                }
                repaint();
            }
        });

        JButton button = new JButton("Reset");
        setContentPane((new JPanel()).add(button).getParent());
        button.addActionListener(e->{
            m_controlPoints.clear();
            m_evaluatePoints.clear();
            repaint();
        });
        pack();
        setVisible(true);
    }
    /** 描画する際の線の幅 */
    private static final float STROKE_WIDTH = 1.5f;
    /** 点を描画する際の半径 */
    private static final double POINT_RADIUS = 3.0;

    /** 制御点列　*/
    private final List<Point2D> m_controlPoints = new ArrayList<>();
    /** 評価点列 */
    private List<Point2D> m_evaluatePoints = new ArrayList<>();
}