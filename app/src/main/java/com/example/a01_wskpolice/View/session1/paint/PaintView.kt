package com.example.a01_wskpolice.View.session1.paint

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.a01_wskpolice.View.session1.paint.PaintActivity.Companion.paintBrush
import com.example.a01_wskpolice.View.session1.paint.PaintActivity.Companion.path

class PaintView : View {
    var params: ViewGroup.LayoutParams? = null // Объявление переменной для параметров макета представления
    companion object {
        // Список путей для рисования
        var pathList = ArrayList<Path>()
        // Текущий цвет кисти для рисования
        var currentBrush = Color.BLACK
        // Список цветов
        var colorList = ArrayList<Int>()
    }
    // Конструкторы
    constructor(context: Context) : this(context, null){
        init() // Инициализация при создании представления без атрибутов
    }
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0){
        init() // Инициализация при создании представления с атрибутами
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init() // Инициализация при создании представления с атрибутами и стилем
    }
    private fun init(){
        paintBrush.isAntiAlias = true // Включение сглаживания
        paintBrush.color = currentBrush // Установка цвета кисти
        paintBrush.style = Paint.Style.STROKE // Установка стиля рисования - обводка
        paintBrush.strokeJoin = Paint.Join.ROUND // Установка типа соединения линий - круглые концы
        paintBrush.strokeWidth = 8f // Установка ширины линии

        params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) // Настройка параметров макета
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x // Получение координаты по оси X
        val y = event.y // Получение координаты по оси Y
        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x,y) // Установка начальной точки пути при касании экрана
                return true // Возврат true для обработки события
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x,y) // Добавление линии к пути при перемещении по экрану
                pathList.add(path) // Добавление пути в список путей
                colorList.add(currentBrush) // Добавление цвета в список цветов

            }
            else -> return false // Возврат false в случае, если событие не обработано
        }
        postInvalidate() // Перерисовка представления
        return false // Возврат false после обработки события
    }
    override fun onDraw(canvas: Canvas) {

        for (i in pathList.indices){
            paintBrush.setColor(colorList[i]) // Установка цвета кисти
            canvas.drawPath(pathList[i], paintBrush) // Рисование пути на канве
            invalidate() // Запрос на перерисовку представления
        }

    }

}
