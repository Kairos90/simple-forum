function duplicate(selector) {
    var e = $(selector);
    var c = e.clone();
    var i = c.find("input");
    i.attr("name", i.attr("name") + "1");
    c.insertAfter(e).show();
    return true;
}

