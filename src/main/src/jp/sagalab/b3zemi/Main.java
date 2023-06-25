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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main extends JFrame {

    /**
     * @param _args the command line arguments
     */
    public static void main(String[] _args) {
//        canMove = true;
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
            for (int i = 0; i < m_controlPoints.size(); ++i){
                drawPoint(m_controlPoints.get(i), Color.RED, _g);
            }
        }

        if (m_controlPoints.size() > 1){
            for (int i = 0; i < m_controlPoints.size()-1; ++i){
                drawLine(m_controlPoints.get(i), m_controlPoints.get(i+1), Color.gray, _g);
            }
        }

        if (m_evaluatePoints.size() > 0){
            for (int i = 0; i < m_evaluatePoints.size()-1; ++i){
                drawLine(m_evaluatePoints.get(i), m_evaluatePoints.get(i+1), Color.blue, _g);
            }
        }

        if(m_internalPoints.size() > 0){
            int degree = m_controlPoints.size() - 1;
            int next = 0;
            while (degree > 1){
                int pre = 0;
                for(int i = next; i < next + degree - 1; ++i){
                    drawLine(m_internalPoints.get(i), m_internalPoints.get(i+1), Color.GREEN, _g);
                    drawPoint(m_internalPoints.get(i), Color.black, _g);
                    drawPoint(m_internalPoints.get(i+1), Color.black, _g);
                    ++pre;
                }
                next += pre + 1;
                --degree;
            }
            drawPoint(m_internalPoints.get(next), Color.RED, _g);
        }
    }

    /**
     * 指定した点を指定した色でパネルに描画します．
     * @param _point 点
     * @param _color 色
     * @param _g     グラフィックス
     */
    private void drawPoint(Point2D _point, Color _color, Graphics _g) {
        Graphics2D g = (Graphics2D)_g;
        g.setColor(_color);
        g.setStroke(new BasicStroke(STROKE_WIDTH));
        g.draw(new Ellipse2D.Double(_point.getX() - POINT_RADIUS, _point.getY() - POINT_RADIUS, POINT_RADIUS * 2, POINT_RADIUS * 2));
    }

    /**
     * 指定した２つの点の座標を結ぶ線分を指定した色で描画します．
     * @param _p1    1つ目の点
     * @param _p2    ２つ目の点
     * @param _color 色
     * @param _g     グラフィックス
     */
    private void drawLine(Point2D _p1, Point2D _p2, Color _color, Graphics _g) {
        Graphics2D g = (Graphics2D)_g;
        g.setColor(_color);
        g.setStroke(new BasicStroke(STROKE_WIDTH));
        g.draw(new Line2D.Double(_p1.getX(), _p1.getY(), _p2.getX(), _p2.getY()));
    }

    /**
     * 制御点列からBezierCurveのインスタンスを生成し，評価点列を求めます.
     */
    private void calculate() {
        BezierCurve bezierCurve = BezierCurve.create(m_controlPoints);
        List<Point2D> evaluatePoints = new ArrayList<>();

        for(double t = 0; t < T; ++t){
            evaluatePoints.add(bezierCurve.evaluate(t/T));
        }
        m_evaluatePoints = evaluatePoints;
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
                if(!moveFlag){
                    m_controlPoints.add(new Point2D.Double(e.getX(),e.getY()));

                    if(m_controlPoints.size() > 2){
                        calculate();
                        if(moveCount > 0){
                            setInternalPoints(moveCount/T);
                        }
                    }
                    repaint();
                }
            }
        });

        JPanel pMain = new JPanel();
        JButton bReset = new JButton("Reset");
        pMain.add(bReset);
        setContentPane(pMain);
        bReset.addActionListener(e -> {
            m_controlPoints.clear();
            m_evaluatePoints.clear();
            m_internalPoints.clear();
            moveFlag = false;
            repaint();
        });

        JButton bStart = new JButton("Start");
        pMain.add(bStart);
        setContentPane(pMain);
        bStart.addActionListener(e -> {
            if(m_controlPoints.size() > 2 && !moveFlag){
                moveFlag = true;
                bStart.setText("Stop");
            }else if(moveFlag){
                moveFlag = false;
                bStart.setText("Start");
            }
        });

        if(!canMove){
           bStart.setEnabled(false);
        }

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(() -> {
            if(moveFlag){
                if(moveCount < T){
                    ++moveCount;
                    setInternalPoints(moveCount/T);
                }else{
                    moveCount = 0;
                    moveFlag = false;
                    bStart.setText("Start");
                }
                repaint();
            }
        },0,T, TimeUnit.MILLISECONDS);

        pack();
        setVisible(true);
    }

    private void setInternalPoints(double _t){
        m_internalPoints.clear();
        int degree = m_controlPoints.size() - 1;
        List<Point2D> copyPoints = new ArrayList<>(m_controlPoints);
        List<Point2D> prePoints = new ArrayList<>();
        while (degree > 0){
            prePoints.clear();
            for (int i = 0; i < degree; ++i){
                Point2D p = BezierCurve.internal(copyPoints.get(i),copyPoints.get(i+1),_t);
                prePoints.add(p);
                m_internalPoints.add(p);
            }
            copyPoints = new ArrayList<>(prePoints);
            --degree;
        }
    }

    /** 描画する際の線の幅 */
    private static final float STROKE_WIDTH = 1.5f;
    /** 点を描画する際の半径 */
    private static final double POINT_RADIUS = 3.0;
    /** 分割数 **/
    private static final int T = 100;

    /** 制御点列　*/
    private final List<Point2D> m_controlPoints = new ArrayList<>();
    /** 評価点列 */
    private List<Point2D> m_evaluatePoints = new ArrayList<>();

    static private boolean canMove = false;
    private boolean moveFlag = false;

    private List<Point2D> m_internalPoints = new ArrayList<>();

    private double moveCount = 0.0;
}