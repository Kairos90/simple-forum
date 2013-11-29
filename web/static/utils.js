function duplicate(selector) {
    var e = $(selector);
    e.clone().insertAfter(e).show(500);
    return true;
}

