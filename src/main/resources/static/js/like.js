
// 페이지 로드 시 실행되는 함수
$(document).ready(function() {
    updateLikeStatuses();
});

// 좋아요 상태를 업데이트하는 함수
function updateLikeStatuses() {
    $(".stock-item").each(function() {
        var stockId = $(this).data("stock-id");
        checkLikeStatus(stockId, $(this));
    });
}

// 서버에 좋아요 상태를 확인하는 함수
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
            updateLikesList(response);  // 좋아요 리스트 업데이트 함수 호출
   // 좋아요 버튼의 클래스 상태 변경
           var likeButton = $('button[onclick*="toggleLike(' + stockId + ')"]');
           likeButton.toggleClass('liked', response.like); // 여기서 response.liked는 서버로부터의 응답에 따라 달라짐

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
            updateLikesList(response);  // 좋아요 리스트 업데이트 함수 호출
   // 좋아요 버튼의 클래스 상태 변경
           var likeButton = $('button[onclick*="toggleLike(' + stockId + ')"]');
           likeButton.toggleClass('liked', response.like); // 여기서 response.liked는 서버로부터의 응답에 따라 달라짐


        },
        error: function(error) {
            console.error("Error toggling like for stock ID: " + stockId);
        }
    });
}























function updateLikesList(likes) {
  var likesListHtml = '<div class="stock-list">';
  likes.forEach(function(like) {
    // `like.mainDataEntity` 객체에서 필요한 데이터를 추출하여 사용합니다.



    var name = like.mainDataEntity.dataEntity.englishName; // 주식명
    var code = like.mainDataEntity.stockCodes; // 주식코드
    var price = like.mainDataEntity.stckPrprInUSD; // 주식 현재가 USD
    var priceChange = like.mainDataEntity.prdyVrss; // 가격 변동
    var percentChange = like.mainDataEntity.prdyCtrt; // 퍼센트 변동
    var stock_id = like.mainDataEntity.id

    // 가격 변동에 따른 색상 클래스를 결정합니다.
    var changeClass = percentChange >= 0 ? 'positive' : 'negative';

    // 각 좋아요 항목의 HTML을 구성합니다.
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



