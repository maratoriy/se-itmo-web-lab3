const sc = new SquareCanvas("#graph");

const rd = 40
const rd2 = rd/2;
const c = 50
const ed = 100

// Draw the graph with labels
function draw({r}) {



    sc.updateArea();
    sc.ctx.lineWidth = 3;
    sc.shape("#788C9C", "#64B5F6",
        c, c,
        {type: "lineTo", x: c, y: c+rd2},
        {type: "arcTo", x1: c-rd2, y1: c+rd2, x2: c-rd2, y2: c, radius: rd2},
        {type: "lineTo", x: c-rd, y: c},
        {type: "lineTo", x: c-rd, y: c-rd2},
        {type: "lineTo", x: c, y: c-rd2},
        {type: "lineTo", x: c, y: c-rd},
        {type: "lineTo", x: c+rd, y: c},
        {type: "lineTo", x: c, y: c}
    )
    sc.ctx.lineWidth = 1;
    sc.ctx.fillStyle = "white"
    sc.ctx.strokeStyle = "white"

    let rLabel = (isNaN(r / 1)) ? "R" : r;
    let r2Label = (isNaN(r / 2)) ? "R/2" : r / 2;

    sc.line(0, c, ed, c); // Ox
    sc.line(c, 0, c, ed); // Oy

    sc.line(c-rd, c-1.5, c-rd, c+1.5); // | -R
    sc.fillText(`-${rLabel}`, c-rd+1, c-1.5, 0.8);

    sc.line(c-rd2, c-1.5, c-rd2, c+1.5); // | -R/2
    sc.fillText(`-${r2Label}`, c-rd2+1, c-1.5, 0.8);

    sc.line(c+rd, c-1.5, c+rd, c+1.5); // | R
    sc.fillText(`${rLabel}`, c+rd+1, c-1.5, 0.8);

    sc.line(c+rd2, c-1.5, c+rd2, c+1.5); // | R/2
    sc.fillText(`${r2Label}`, 71, c-1.5, 0.8);

    sc.line(c-1.5, c-rd, c+1.5, c-rd); // - R
    sc.fillText(`${rLabel}`, c+2, c-rd+1, 0.8);

    sc.line(c-1.5, c-rd2, c+1.5, c-rd2); // - R/2
    sc.fillText(`${r2Label}`, c+2, c-rd2+1, 0.8);

    sc.line(c-1.5, c+rd2, c+1.5, c+rd2); // - -R/2
    sc.fillText(`-${r2Label}`, c+2, 71, 0.8);


    sc.line(c-1.5, c+rd, c+1.5, c+rd); // - -R
    sc.fillText(`-${rLabel}`, c+2, c+rd+1, 0.8);

    sc.line(c-1.5, 3, c, 0);  // /\
    sc.line(c+1.5, 3, c, 0);  // ||
    sc.fillText("y", c-5, 4);

    sc.line(97, c+1.5, ed, c);
    sc.line(97, 48.5, ed, c); // ->
    sc.fillText("x", ed-5, c-3);

    // dotArray.forEach((dot, index) => {
    //     sc.fillText(`${dotArray.length-index}`, dot.x+0.5, dot.y-0.5, 0.8);
    //     sc.dot(dot.x, dot.y, "#702963")
    // })

    updateDots()
}

let rValue = initR()

function setR(r) {
    rValue = r;
    draw({r})
}

let hidx =  document.getElementById("my-form:input-x")
let hidy = document.getElementById("my-form:input-y")
let subbut = document.getElementById("my-form:submit-button")

function updateDots() {
    document.querySelectorAll('#results-table_data tr').forEach((iter) => {
        let x = (iter.children[0].innerHTML*4/10/rValue+0.5)*100
        let y = -(iter.children[1].innerHTML*4/10/rValue-0.5)*100
        sc.dot(x,y, iter.children[3].classList.contains("hit") ? "green" : "red")
    });
}

sc.onclick = (e) => {
    let x = (e.x / 100 - 0.5) * 10 / 4 * rValue;
    let y = (-e.y / 100 + 0.5) * 10 / 4 * rValue;

    console.log(x,y)

    hidx.value = x
    hidy.value = y

    subbut.click()

    location.reload()
}

// Draw on load
window.addEventListener("load", () => {
    draw({r: rValue})
})

// Redraw on resize
window.addEventListener("resize", () => draw({r: rValue}));



