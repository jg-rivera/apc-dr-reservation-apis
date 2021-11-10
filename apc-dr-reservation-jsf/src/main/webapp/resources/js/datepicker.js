$(function () {
    $(".datepicker").datepicker({
        minDate: "+1D",
        maxDate: "+1M",
        beforeShowDay: function (date) {
            var day = date.getDay();
            return [(day != 0 && day != 6), ''];
        }
    });
});