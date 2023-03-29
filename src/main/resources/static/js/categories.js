// Dynamically updates the table based on the filter dropdown
$('#filter').on('change', function () {
  $.ajax({
    url: '/products/list',
    type: 'GET',
    data: {
      filter: $(this).val(),
    },

    success: function (filter) {
      if ($("#filter").val() == 0) {
        location.reload();
        return;
      }

      $('#product').empty();

      $(filter).each(function () {

        
      })
    }
  })
})