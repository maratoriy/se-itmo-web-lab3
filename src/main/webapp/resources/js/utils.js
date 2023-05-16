/*
    Class provides easier modification of table
 */
class TableModifier {

    constructor(target) {
        this.table = document.querySelector(target);
        this.header = this.table.innerHTML;
        if (!this.table)
            console.log("Failed to initialise table at target: " + target);
        this.columns = this.table.rows[0].cells.length;
    }

    addRow(...cols) {
        let row = document.createElement("tr");
        for (let i = 0; i < cols.length && i < this.columns; i++) {
            let col = document.createElement("td")
            col.appendChild(document.createTextNode(cols[i]));
            row.appendChild(col);
        }
        this.table.appendChild(row);
    }

    setContent(html) {
        this.table.innerHTML = this.header + html;
    }

    clear() {
        this.table.innerHTML = this.header;
    }
}

/*
    Class provides square (100x100) canvas with:
        auto-scaling;
        fixing problem with high-resolution screens
        path-like shape drawing
*/
class SquareCanvas {


    onclick;

    constructor(target) {
        this.canvas = document.querySelector(target);
        if (this.canvas && this.canvas.getContext) {
            this.ctx = this.canvas.getContext("2d");
            this.ctx.scale(devicePixelRatio, devicePixelRatio);
            this.updateArea();

            this.canvas.addEventListener("click", (e) => {
                let rect = this.canvas.getBoundingClientRect();
                if (this.onclick)
                    this.onclick({x: e.offsetX / rect.width * 100, y: e.offsetY / rect.height * 100});
            })
        } else
            console.log("Failed to setup SquareCanvas on target: " + target.toString());
    }

    updateArea() {
        this.clear();
        let rect = this.canvas.getBoundingClientRect();
        this.canvas.width = rect.width * devicePixelRatio;
        this.canvas.height = rect.width * devicePixelRatio;
        //this.canvas.style.width = rect.width + 'px';
        this.canvas.style.height = rect.width + 'px';
        this.scale = this.canvas.width / 100;
    }

    clear() {
        this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
    }

    dot(x, y, color) {
        let prevFS = this.ctx.fillStyle;
        let prevSS = this.ctx.strokeStyle;

        this.ctx.fillStyle = color;
        this.ctx.strokeStyle = color;

        this.ctx.beginPath();
        this.ctx.arc(x * this.scale, y * this.scale, 2, 0, 2 * Math.PI);
        this.ctx.fill();

        this.ctx.fillStyle = prevFS;
        this.ctx.strokeStyle = prevSS;
    }

    line(x1, y1, x2, y2) {
        this.ctx.beginPath();
        this.ctx.moveTo(x1 * this.scale, y1 * this.scale);
        this.ctx.lineTo(x2 * this.scale, y2 * this.scale);
        this.ctx.stroke();
    }

    lineTo({x, y}) {
        this.ctx.lineTo(x * this.scale, y * this.scale);
    }

    arcTo({x1, y1, x2, y2, radius}) {
        this.ctx.arcTo(x1 * this.scale, y1 * this.scale, x2 * this.scale, y2 * this.scale, radius * this.scale);
    }

    shape(fillStyle, strokeStyle, initX, initY, ...line) {
        let prevFS = this.ctx.fillStyle;
        let prevSS = this.ctx.strokeStyle;

        this.ctx.fillStyle = fillStyle;
        this.ctx.strokeStyle = strokeStyle;

        this.ctx.beginPath();
        this.ctx.moveTo(initX * this.scale, initY * this.scale);

        for (let key in line) {
            let iter = line[key];
            switch (iter.type) {
                case "lineTo":
                    this.lineTo(iter);
                    break;
                case "arcTo":
                    this.arcTo(iter);
                    break;
            }
        }

        this.ctx.fill();
        this.ctx.stroke();

        this.ctx.fillStyle = prevFS;
        this.ctx.strokeStyle = prevSS;
    }


    fillText(text, x, y, scale = 1) {
        this.ctx.font = `${this.scale * 4 * scale}pt Roboto Slab`;
        this.ctx.fillText(text, x * this.scale, y * this.scale);
    }


}

// Converts FormData to object
function formDataToObject(formData = new FormData()) {
    let object = {};
    formData.forEach((value, key) => {
        if(!Reflect.has(object, key)){
            object[key] = value;
            return;
        }
        if(!Array.isArray(object[key])){
            object[key] = [object[key]];
        }
        object[key].push(value);
    });
    return object;
}

// Converts object to FormData
function objectToFormData(object) {
    const formData = new FormData();
    Object.keys(object).forEach(key => formData.append(key, object[key]));
    return formData;
}

// GET XMLHttpRequest wrapper
function get_request(link, event) {
    let xhr = new XMLHttpRequest();
    if (event)
        xhr.onloadend = () => event(xhr.response);
    xhr.open("GET", link);
    xhr.send();
}

// POST XMLHttpRequest wrapper
function post_request(link, event, data = {}) {
    let xhr = new XMLHttpRequest()
    if (event)
        xhr.onloadend = () => event(xhr.response);
    xhr.open("POST", link)

    let formData = objectToFormData(data);
    xhr.send(formData)
}

function post_reload() {
    setTimeout(() => location.reload(), 250)
}