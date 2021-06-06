const textArea = document.getElementById("content");
const headingArea = document.getElementById("heading");
const outputRenderedText = document.getElementById("rendered_content");
const config = {
    omitExtraWLInCodeBlocks: true,
    noHeaderId: false,
    parseImgDimensions: true,
    simplifiedAutoLink: true,
    literalMidWordUnderscores: true,
    strikethrough: true,
    tables: true,
    tablesHeaderId: false,
    ghCodeBlocks: true,
    tasklists: true,
    smoothLivePreview: true,
    prefixHeaderId: false,
    disableForced4SpacesIndentedSublists: false,
    ghCompatibleHeaderId: true,
    smartIndentationFix: false
};
const converter = new showdown.Converter(config); // showdown.js

function insertAtCaret(areaId,text) {
    let txtarea = document.getElementById(areaId);
    let scrollPos = txtarea.scrollTop;
    let strPos = 0;
    let br = ((txtarea.selectionStart || txtarea.selectionStart == '0') ?
        "ff" : (document.selection ? "ie" : false ) );
    if (br == "ie") {
        txtarea.focus();
        let range = document.selection.createRange();
        range.moveStart ('character', -txtarea.value.length);
        strPos = range.text.length;
    }
    else if (br == "ff") strPos = txtarea.selectionStart;

    let front = (txtarea.value).substring(0,strPos);
    let back = (txtarea.value).substring(strPos,txtarea.value.length);
    txtarea.value=front+text+back;
    strPos = strPos + text.length;
    if (br == "ie") {
        txtarea.focus();
        let range = document.selection.createRange();
        range.moveStart ('character', -txtarea.value.length);
        range.moveStart ('character', strPos);
        range.moveEnd ('character', 0);
        range.select();
    }
    else if (br == "ff") {
        txtarea.selectionStart = strPos;
        txtarea.selectionEnd = strPos;
        txtarea.focus();
    }
    txtarea.scrollTop = scrollPos;
}

/**
 * This method inserts text-formatting templates into an globally defined DOM element
 * */
function format(action){

    switch (action){
        case "strong" : {
            // textArea.value += "**strong text here**";
            insertAtCaret("content", "**strong text here**");
            break;
        }
        case "italic" : {
            insertAtCaret("content", "*italic text here*");
            break;
        }
        case "code" : {
            insertAtCaret("content", "```\ncode here\n```");
            break;
        }
        case "ul" : {
            insertAtCaret("content", "- list item here\n- next list item here\n");
            break;
        }
        case "ol" : {
            insertAtCaret("content", "1. numbered item here\n2. next here\n");
            break;
        }
        case "link" : {
            insertAtCaret("content", "[link_ref_name]\n");
            textArea.value += "\n[link_ref_name]: https://www.binobo.ddns.net\n";
            break;
        }
        case "strikeout" : {
            insertAtCaret("content", "~~struck-out text here~~");
            break;
        }
        case "h2" : {
            insertAtCaret("content", "## sub-heading here\n");
            break;
        }
        case "h3" : {
            insertAtCaret("content", "### sub-sub-heading\n");
            break;
        }
        case "formula" : {
            // textArea.value += "\\(x = {-b \\pm \\sqrt{b^2-4ac} \\over 2a}\\)";
            // insertAtCaret("content", "```latex\n$$\n_1x_2=\\frac{-b\\pm\\sqrt{b^2-4ac}}{2a}\n$$\n````");
            insertAtCaret("content", "$$\n_1x_2=\\frac{-b\\pm\\sqrt{b^2-4ac}}{2a}\n$$");
            break;
        }
        case "katex_inline" : {
            // textArea.value += "\\(x = {-b \\pm \\sqrt{b^2-4ac} \\over 2a}\\)";
            // insertAtCaret("content", "```latex\n$$\n_1x_2=\\frac{-b\\pm\\sqrt{b^2-4ac}}{2a}\n$$\n````");
            insertAtCaret("content", " $c = \\sqrt{a^2 + b^2}$ ");
            break;
        }
        case "img" : {
            insertAtCaret("content", "![img-ref-name]\n");
            textArea.value += "\n[img-ref-name]: https://i.ibb.co/R0zmRcC/blog-bg-3.jpg\n";
            break;
        }
        case "table" : {
            insertAtCaret("content", "| Tables        | Are           | Cool  |\n" +
                                                "| ------------- |:-------------:| -----:|\n" +
                                                "| **col 3 is**  | right-aligned | $1600 |\n" +
                                                "| col 2 is      | *centered*    |   $12 |\n" +
                                                "| zebra stripes | ~~are neat~~  |    $1 |\n\n");
            break;
        }
    }

    render_Post(textArea.value);
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

function stripHTML(text){
    return String(text).replace(/<[^>]+>/gm, '');
}

// FIXME
function preformatInput(text){

    // look for html tags outside of tripple- and single-backticks
    // /```([\s\S]*?)```/g --> pattern um nach code-blöcken zu suchen
    // alles was nicht durch dieses pattern erfasst wird, wird nach html-tags durchsucht und gefiltert
    let from_i = [];
    let to_i = [];
    let res = "";
    let pattern = /\n```[\s\S]*?\n```\n/g; // TODO change, so that singe and triple back ticks are recognized
    let splittedText = text.split(pattern);
    let textToReplace = text;
    let noCode = [];
    for(let i = 0; i<splittedText.length; i++){
        // indizes.push(text.find(i));
        if(i>0)from_i.push(text.indexOf(splittedText[i], text.indexOf(splittedText[i-1])));
        else from_i.push(text.indexOf(splittedText[i]));

        to_i.push(splittedText[i].length);
    }
    for(let i = 0; i<splittedText.length; i++)
        splittedText[i] = stripHTML(splittedText[i]);

    // res = text.substring(0, from_i.length > 0 ? from_i[0] : 0);
    for(let i = 0; i<splittedText.length; i++){
        if(!pattern.test(splittedText[i]))
            noCode.push(splittedText[i]);
    }
    console.log(splittedText);
    console.log(noCode);
    // splittedText = [];
    for(let i of noCode){
        textToReplace = textToReplace.replace(i, '---DELIMITER---');
    }
    console.log(textToReplace);
    splittedText = textToReplace.split('---DELIMITER---');
    let formattedText = [];
    for(let i = 0; i<noCode.length; i++)
        formattedText.push(stripHTML(noCode[i]));

    let  off = 0;
    for(let i = 0, j = 0; i<splittedText.length || j<noCode.length; i++){// TODO fertigstellen!
        if(pattern.test(splittedText[i]))res += splittedText[i];
        else {
            res += noCode[j];
            j++;
        }
    }

    console.log(res);
}

/**
 * This function writes the content from the textfield to the DOM so that it gets interpreted as HTML
 */
function render_Post(value, outputTo){
    // TODO detect, if there are not-allowed tags inside the textarea --> regex? "<\s*([^ >]+)[^>]*>.*?<\s*/\s*\1\s*>"
    outputTo.innerHTML = converter.makeHtml(value);

    hljs.highlightAll(); // highlight.js

    renderMathInElement(
        document.body,
        {
            delimiters: [
                {left: "$$", right: "$$", display: true},
                {left: "\\[", right: "\\]", display: true},
                {left: "$", right: "$", display: false},
                {left: "\\(", right: "\\)", display: false}
            ]
        }
    );
}

render_Post();