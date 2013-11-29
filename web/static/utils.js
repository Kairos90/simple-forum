function duplicate(selector) {
    var e = $(selector);
    e.clone().insertAfter(e);
}

