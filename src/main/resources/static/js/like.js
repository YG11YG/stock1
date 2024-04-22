
$(document).ready(function() {
    updateLikeStatuses();
});


function updateLikeStatuses() {
    $(".stock-item").each(function() {
        var stockId = $(this).data("stock-id");
        checkLikeStatus(stockId, $(this));
    });
}

function checkLikeStatus(stockId, element) {
    $.ajax({
        url: '/likes/status/' + stockId,
        type: 'GET',
        success: function(isLiked) {
            if (isLiked) {
                element.find('button[onclick*="toggleLike(' + stockId + ')"]').addClass('liked');
            }
        },
        error: function(error) {
            console.error("Error checking like status for stock ID: " + stockId);
        }
    });
}

function toggleLike(stockId) {
    $.ajax({
        url: '/likes/toggle/' + stockId,
        type: 'POST',
        success: function(response) {
            console.log("Toggle Like successful for stock ID: " + stockId);
            console.log(response);
            updateLikesList(response);

           var likeButton = $('button[onclick*="toggleLike(' + stockId + ')"]');
           likeButton.toggleClass('liked', response.like);

            location.reload();
        },
        error: function(error) {
            console.error("Error toggling like for stock ID: " + stockId);
        }
    });
}



function toggleLike2(stockId) {
    $.ajax({
        url: '/likes/toggle/' + stockId,
        type: 'POST',
        success: function(response) {
            console.log("Toggle Like successful for stock ID: " + stockId);
            console.log(response);
            updateLikesList(response);

           var likeButton = $('button[onclick*="toggleLike(' + stockId + ')"]');
           likeButton.toggleClass('liked', response.like);


        },
        error: function(error) {
            console.error("Error toggling like for stock ID: " + stockId);
        }
    });
}



function updateLikesList(likes) {
  var likesListHtml = '<div class="stock-list">';
  likes.forEach(function(like) {

    var name = like.mainDataEntity.dataEntity.englishName;
    var code = like.mainDataEntity.stockCodes;
    var price = like.mainDataEntity.stckPrprInUSD;
    var priceChange = like.mainDataEntity.prdyVrss;
    var percentChange = like.mainDataEntity.prdyCtrt;
    var stock_id = like.mainDataEntity.id


    var changeClass = percentChange >= 0 ? 'positive' : 'negative';

    likesListHtml += `
      <div class="stock-item">
        <div class="stock-info">
          <div class="stock-name">${name}</div>
          <div class="stock-code">${code}</div>
        </div>
         <div class="stock9">
                  <div class="stock-price">${price} USD</div> <!-- 현재 가격 -->
                  <div class="stock-change ${changeClass}">
                    <span>${percentChange >= 0 ? '▲' : '▼'} ${Math.abs(priceChange)}</span> <!-- 변동 값 -->
                    <span>${percentChange}%</span> <!-- 전일 대비율 -->
                  </div>
                </div>
           <button class="like-button yes" onclick="toggleLike(${stock_id})">♥</button>
               </div>
            `;
  });
  likesListHtml += '</div>';
  $("#likesList").html(likesListHtml);
}



