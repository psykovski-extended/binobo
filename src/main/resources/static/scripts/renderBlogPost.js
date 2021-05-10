
const textArea = document.getElementById("content");
const headingArea = document.getElementById("heading");
const outputRenderedText = document.getElementById("rendered_content");

/**
 * This method inserts text-formatting templates into an globally defined dom element
 * */
function format(action){

    switch (action){
        case "strong" : {
            textArea.value += "<strong>strong text here</strong>";
            break;
        }
        case "italic" : {
            textArea.value += "<em>italic text here</em>";
            break;
        }
        case "code" : {
            textArea.value += "<pre><code>code here</code></pre>";
            break;
        }
        case "ul" : {
            textArea.value += `<ul>
    <li>Your bullet item here</li>
</ul>`;
            break;
        }
        case "ol" : {
            textArea.value += `<ol>
    <li>Your numbered item here</li>
</ol>`;
            break;
        }
        case "link" : {
            textArea.value += `<a href="your URL here">link text here</a>`;
            break;
        }
        case "strikeout" : {
            textArea.value += `<s>struck-out text here</s>`;
            break;
        }
        case "h2" : {
            textArea.value += `<h2>sub-heading here</h2>`;
            break;
        }
        case "h3" : {
            textArea.value += `<h3> sub-sub-heading</h3>`;
            break;
        }
        case "formula" : {
            textArea.value += `\\(x = {-b \\pm \\sqrt{b^2-4ac} \\over 2a}\\)`;
            break;
        }
    }

    render_Post();
}

/**
 * This Method inserts and replaces the specified fragment at the given position with the parsed ins_string
 * @param main_string String to insert into
 * @param ins_string String to insert and replace
 * @param pos Position where to insert the parsed string
 * @param fragmentToReplace this is just needed to determine the length of replaced fragment
 * @return returns the modified string
 * */
function insertAndReplace(main_string, ins_string, pos, fragmentToReplace) {
    if(typeof(pos) == "undefined") {
        pos = 0;
    }
    if(typeof(ins_string) == "undefined") {
        ins_string = '';
    }
    return main_string.slice(0, pos) + ins_string + main_string.slice(pos + fragmentToReplace.length);
}

/**
 * This function writes the content from the textfield to the DOM so that it gets interpreted as HTML
 */
function render_Post(){
    let textToRender = textArea.value;

    for(let i = textToRender.indexOf('<code>', 0); i<textToRender.length; i++){
        if(textToRender.indexOf('<code>', i) !== -1 && textToRender.indexOf('</code>', textToRender.indexOf('<code>', i)) !== -1){
            let inner = textToRender.substring(textToRender.indexOf('<code>', i) + 6, textToRender.indexOf('</code>', textToRender.indexOf('<code>', i)));
            inner = inner.replaceAll('<', '&lt').replaceAll('>', '&gt');

            textToRender = textToRender.substring(0, textToRender.indexOf('<code>', i) + 6) + inner + textToRender.substring(textToRender.indexOf('</code>', textToRender.indexOf('<code>', i)));

            i = textToRender.indexOf('</code>', textToRender.indexOf('<code>', i));
            console.log(textToRender);
        }
    }

    textToRender = '<h1 id="post-heading">' + headingArea.value + '</h1>' + '<p>' + textToRender + '</p>';
    outputRenderedText.innerHTML = textToRender;


    // let contentToRender = document.getElementsByTagName('code');
    //
    // for(let i of contentToRender){
    //     i.innerHTML = i.innerHTML.replaceAll('<', '&lt').replaceAll('>', '&gt');
    // }

    hljs.highlightAll();

    MathJax.typeset()
}

render_Post();