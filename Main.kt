import com.sun.awt.AWTUtilities
import javax.swing.*
import java.awt.*
import java.awt.event.*

class Main:JFrame(){
    var xOld = 0//记录窗口移动的变量
    var yOld = 0//记录窗口移动的变量
    var robot = Robot()//控制鼠标操作
    var ist = false//记录点击次数
    var x1=0//记录点击位置
    var y1=0//记录点击位置
    val times = 3.6 //距离与时间比率
    var title = JLabel("跳一跳辅助 - 请点击小人位置")
    init{
        var top = JPanel() //顶部移动面板
        top.background=Color.white //设置顶部面板颜色
        top.border=BorderFactory.createLineBorder(Color.blue) //设置顶部面板边框颜色
        top.preferredSize=Dimension(400,30)  //设置顶部面板高度
        top.add(title) //设置标题
        add(top,BorderLayout.NORTH) //添加顶部面板到窗口
        setSize(400,720) //设置窗口大小
        setLocationRelativeTo(null) //设置窗口位置
        contentPane.background=Color.black //设置窗口颜色
        defaultCloseOperation=JFrame.EXIT_ON_CLOSE //设置程序关闭方式
        isUndecorated=true //无边框
        isVisible=true //显示窗口
        isAlwaysOnTop=true //程序置顶
        isResizable=true //不可改变窗口大小
        AWTUtilities.setWindowOpacity(this, 0.5f) //窗口透明

        //窗口移动记录事件
        top.addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent) {
                //记录鼠标按下时的坐标
                xOld = e.x
                yOld = e.y
            }
        })
        //窗口拖动事件
        top.addMouseMotionListener(object : MouseMotionAdapter() {
            override fun mouseDragged(e: MouseEvent) {
                //拖拽后，窗口的位置
                setLocation(e.xOnScreen - xOld, e.yOnScreen - yOld)
            }
        })
        //窗口点击事件
        addMouseListener(object:MouseAdapter(){
            override fun mouseReleased(e: MouseEvent) {
                if(ist){
                    //利用勾股定理计算距离
                    var x=0
                    var y=0
                    if(x1>e.x){x=x1-e.x}else{x=e.x-x1}
                    if(y1>e.y){y=y1-e.y}else{y=e.y-y1}
                    var length=Math.sqrt(x*x+y*y+0.0)*times

                    //模拟鼠标点击
                    extendedState=JFrame.ICONIFIED //最小化窗口
                    robot.delay(250)
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK) //按下鼠标
                    robot.delay(length.toInt()) //间隔时间
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK) //放下鼠标
                    robot.delay(600)
                    extendedState=JFrame.NORMAL //恢复窗口
                    ist=false
                    title.text="请点击小人位置"
                }else{
                    x1=e.x
                    y1=e.y
                    ist=true
                    title.text="请点击目标方块位置"
                }
            }
        })
    }
}

fun main(args: Array<String>) {
    Main()
}