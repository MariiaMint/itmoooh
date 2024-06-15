const canvasPlot = document.querySelector('#canvas')
const ctx = canvasPlot.getContext(`2d`);

//Рисуем сетку
const canvasPlotWidth = canvasPlot.clientWidth;
const canvasPlotHeight = canvasPlot.clientHeight;
const scaleX = 40; //расстояние между элементами сетки по Х
const scaleY = 40; //расстояние между элементами сетки по Y

const xAxis = Math.round(canvasPlotWidth / scaleX / 2) * scaleX;
const yAxis = Math.round(canvasPlotHeight / scaleY / 2) * scaleY;

function drawAxes() {
// Выбор шрифта и его вид, размер

    ctx.font = `${Math.round(scaleX / 2)}px Arial`;
    ctx.textAlign = `left`;
    ctx.textBaseline = `top`;
    ctx.beginPath();
    ctx.strokeStyle = `#f5f0e8`;

    let zz = 5; //Зазор между осями и числами нумерации чисел

    for (let i = 0; i <= canvasPlotWidth; i = i + scaleX) {
        ctx.moveTo(i, 0);
        ctx.lineTo(i, canvasPlotHeight); // нанесение вертикальных линий разметки
        ctx.fillText((i - xAxis) / scaleX, i + zz, yAxis + zz); //нанесение числовых значений на ось Х
    }

    for (let i = 0; i <= canvasPlotHeight; i = i + scaleY) {
        ctx.moveTo(0, i);
        ctx.lineTo(canvasPlotWidth, i); // нанесение горизонтальных линий разметки
        ctx.fillText((yAxis - i) / scaleY, xAxis + zz, i + zz); //нанесение числовых значений на ось Y
    }
    ctx.stroke();
    ctx.closePath(); //закрыть путь, чтобы рисовать новые линии другим цветом


    const zxy = 50; //Зазор между осями и обозначением этих осей  x,y
    ctx.beginPath();
    ctx.strokeStyle = `#000000`;
    ctx.moveTo(xAxis, 0);
    ctx.lineTo(xAxis, canvasPlotHeight);
    ctx.fillText(`y`, xAxis - zxy, 0); // означение оси Y
    ctx.moveTo(0, yAxis);
    ctx.lineTo(canvasPlotWidth, yAxis);
    ctx.fillText(`x`, canvasPlotWidth - zxy, yAxis - zxy); // означение оси X
    ctx.stroke();
    ctx.closePath(); //закрыть путь, чтобы рисовать новые линии другим цветом
}


//ПРЕОБРАЗОВАНИЕ CANVAS СИСТЕМУ КООРДИНАТ В ДЕКАРТОВУ СИСТЕМУ КООРДИНАТ

// Рисуем график функции
// Рисуем график функции
function drawGraph(r) {
    ctx.clearRect(0, 0, canvasPlotWidth, canvasPlotHeight); // Очистить весь холст
    drawAxes();

    // Треугольник во второй четверти
    ctx.beginPath();
    ctx.fillStyle = `rgba(0, 0, 0, 0.66)`;
    const xTriangle = xAxis;
    const yTriangle = yAxis;

    const xVertex1 = xTriangle;
    const yVertex1 = yTriangle;

    const xVertex2 = xTriangle;
    const yVertex2 = yTriangle - r / 2 * scaleY;

    const xVertex3 = xTriangle + r / 2 * scaleX;
    const yVertex3 = yTriangle;

    ctx.moveTo(xVertex1, yVertex1);
    ctx.lineTo(xVertex2, yVertex2);
    ctx.lineTo(xVertex3, yVertex3);
    ctx.fill();
    ctx.closePath();


    ctx.beginPath();
    ctx.fillStyle = `rgba(0, 0, 0, 0.66)`;

    const xRect = xAxis;
    const yRect = yAxis;

    const widthRect = r/2 * scaleX;
    const heightRect = r * scaleY;

    ctx.fillRect(xRect-widthRect, yRect, widthRect, heightRect);
    ctx.closePath();


    // Четверть круга
    ctx.beginPath();
    ctx.fillStyle = `rgba(0, 0, 0, 0.66)`;
    const xCircle = xAxis;
    const yCircle = yAxis;
    ctx.arc(xCircle, yCircle, r * scaleX, 0, Math.PI / 2);
    ctx.lineTo(xCircle, yCircle);
    ctx.fill();
    ctx.closePath();


}

window.onload = function () {
    ctx.fillStyle = `rgba(0, 0, 0, 0.66)`; // цвет графика чёрный
    drawAxes();
}

function drawDots(points, r) {
    const originalColor = ctx.fillStyle; // Сохранить оригинальный цвет

    // Точки
    points.forEach(point => {
        const x = xAxis + point.x * scaleX;
        const y = yAxis - point.y * scaleY;
        ctx.beginPath();
        ctx.arc(x, y, 3, 0, 2 * Math.PI);

        if (isInArea(point.x, point.y, r)) {
            ctx.fillStyle = 'green';
        } else {
            ctx.fillStyle = 'red';
        }

        ctx.fill();
        ctx.closePath();
    });

    ctx.fillStyle = originalColor; // Восстановить оригинальный цвет
}

function isInArea(x, y, r) {
    let area1_hit = (x >= 0 && y <= 0 && Math.pow(r, 2) >= (Math.pow(x, 2) + Math.pow(y, 2)));
    let area2_hit = (x >= -r/2 && y >= -r && y <= 0 && x <= 0);
    let area3_hit = (y <= r/2 - x && x >= 0 && y >= 0);

    return area1_hit || area2_hit || area3_hit;
}
