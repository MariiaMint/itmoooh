let dots = [];

let xval;
let yval;
let r;

function changeXValue(x) {
    xval = x;
}

function changeYValue(y) {
    yval = y;
}


$(document).ready(function () {
    restoreDataFromLocalStorage();
    drawDots(dots, JSON.parse(localStorage.getItem("formData")).r);

    document.getElementById('data-form:x').value = 0;
    document.getElementById('data-form:y').value = 0;

    const canvas = document.querySelector('#canvas');

    canvas.addEventListener("click", function (event) {
        if (r === null || r === undefined) {
            alert("Невозможно определить координаты точки без радиуса")
            return;
        }

        let x = event.clientX - canvas.getBoundingClientRect().left;
        let y = event.clientY - canvas.getBoundingClientRect().top;

        let graphX = (x - canvas.width / 2) / 40;
        let graphY = (canvas.height / 2 - y) / 40;

        xval = graphX;
        yval = graphY;

        document.getElementById('data-form:hiddenX').value = graphX;
        document.getElementById('data-form:hiddenY').value = graphY;

        document.getElementById('data-form:y').value = 0;

        $('#hiddenAutoFill').val("true");
        $('.submit-button').click();

    });


    // Восстанавливаем данные из localStorage
    function restoreDataFromLocalStorage() {
        dots = JSON.parse(localStorage.getItem('dots')) || [];

        let formData = JSON.parse(localStorage.getItem("formData"));

        let storedData = localStorage.getItem('formData');
        if (storedData) {
            let formData = JSON.parse(storedData);
            xval = 0;
            yval = 0;
            r = formData.r;
        }
        drawGraph(formData.r);
    }

});

// Сохраняем данные в localStorage
function saveDataToLocalStorage() {
    localStorage.setItem('dots', JSON.stringify(dots));
    saveFormData();
}

function handleRSelection(formR, element) {
    r = formR;
    document.getElementById('data-form:hiddenR').value = r;
    drawGraph(r);
    drawDots(dots, r);

    let allLinks = document.querySelectorAll('.selected-link');
    allLinks.forEach(function(link) {
        link.classList.remove('selected-link');
    });

    // Добавить класс 'selected-link' к последнему нажатому элементу
    element.classList.add('selected-link');

}

function saveFormData() {
    localStorage.removeItem("formData");

    let formData = {
        x: xval,
        y: yval,
        r: r
    };
    localStorage.setItem("formData", JSON.stringify(formData));
}

function clearDots() {
    dots = [];
    saveDataToLocalStorage();
    let r = JSON.parse(localStorage.getItem("formData")).r;
    drawGraph(r);
    drawDots(dots, r);
}


function updateHiddenFields() {
    document.getElementById('data-form:hiddenX').value = xval;
    document.getElementById('data-form:hiddenY').value = yval;
    document.getElementById('data-form:hiddenR').value = r;
}

function submitBtn () {
    dots.push({x: xval, y: yval});
    drawDots(dots, r);
    saveDataToLocalStorage();
}