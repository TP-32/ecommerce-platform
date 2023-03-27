let list = document.querySelectorAll(".navigation li");

function activeLink() {
  list.forEach((item) => {
    item.classList.remove("hovered");
  });
  this.classList.add("hovered");
}

list.forEach((item) => item.addEventListener("mouseover", activeLink));

let toggle = document.querySelector(".toggle");
let navigation = document.querySelector(".navigation");
let main = document.querySelector(".main");

toggle.onclick = function () {
  navigation.classList.toggle("active");
  main.classList.toggle("active");
}

// Dynamically updates the table based on the status dropdown
$('#status').on('change', function () {
  $.ajax({
    url: '/admin/orders/list',
    type: 'GET',
    data: {
      status: $(this).val(),
    },

    success: function (status) {
      if ($("#status").val() == 0) {
        location.reload();
        return;
      }

      $('#orderHeader').empty();
      $('#orders').empty();

      $('#orderHeader').append('<tr><td> ID </td> <td> Name </td> <td> Price </td> <td> Order Time </td>');

      // Function to format the date to match a Date() object
      $.formattedDate = function (dateToFormat) {
        var dateObject = new Date(dateToFormat);
        var milli = dateObject.getMilliseconds();
        var second = dateObject.getSeconds();
        var minute = dateObject.getMinutes();
        var hour = dateObject.getHours();
        var day = dateObject.getDate();
        var month = dateObject.getMonth() + 1;
        var year = dateObject.getFullYear();
        day = day < 10 ? "0" + day : day;
        month = month < 10 ? "0" + month : month;
        hour = hour < 10 ? "0" + hour : hour;
        minute = minute < 10 ? "0" + minute : minute;
        second = second < 10 ? "0" + second : second;
        milli = milli < 10 ? "0" : milli;
        var formattedDate = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second + ":" + milli;
        return formattedDate;
      };

      $(status).each(function () {

        $('#orders').append('<tr onclick="rowClicked(\'' + this.id + '\');">' +
          '<td class="id">' + this.id + '</td>' +
          '<td class="name">' + this.user.firstName + ' ' + this.user.lastName + '</td>' +
          '<td> £' + formatPrice(this.price) + '</td>' +
          '<td>' + $.formattedDate(this.time) + '</td></tr>');
      })

      if (status.length == 0) {
        $('#orderHeader').empty();
        $('#orders').append('<td><h2 style="text-align: center;">Oops, this filter has returned no valid records.</h2><h3 style="text-align: center;">Try a different filter!</h3></td>');
      }
    },
    error: function (data, textStatus, errorThrown) {
      console.log(textStatus, errorThrown);
      console.log("Error", data.error)
    }
  })
})

function formatPrice(x) {
  return x.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
}

function customReset(x) {
  location.href = x;
}