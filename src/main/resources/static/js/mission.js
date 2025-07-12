document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("openModalBtn").addEventListener("click", openModal);
    document.getElementById("closeModalBtn").addEventListener("click", closeModal);
})

function initMission(){
    $.ajax({
        type: "POST",
        url: "/api/mission/init",
        success : function(res){
            alert("미션을 초기화했습니다.");
        },
        error : function(error){
            alert("권한이 없습니다");
            window.location.href = '/admin';
        }
    })
}

function openModal() {
    document.getElementById("modal").style.display = "flex";
}

function closeModal() {
    document.getElementById("modal").style.display = "none";
}

var currentPage = 0;
var pageSize = 10;

$(document).ready(function() {
    getMissions(currentPage);

    $('#prevPage').click(function() {
        if (currentPage > 0) {
            getMissions(currentPage - 1);
        }
    });

    $('#nextPage').click(function() {
        getMissions(currentPage + 1);
    });
});

function getMissions(page){
    $.ajax({
        url: '/api/mission/all' + '?page=' + page + '&size=' + pageSize,
        method: 'GET',
        success: function(data) {
            var contents = data.content;
            var tbody = $('#mission-tbody');
            tbody.empty(); // 초기화

            // 데이터에 따라 테이블 행을 생성
            contents.forEach(function(mission) {
                // 한 행의 내용
                var row = `
                        <tr>
                            <td>${mission.id}</td>
                            <td>${mission.content}</td>
                            <td>${mission.activate}</td>
                            <td>
                                <a href="#" onclick="deleteMissionFunction(${mission.id})">삭제</a>
                            </td>
                        </tr>
                    `;
                tbody.append(row);

                currentPage = data.number;

                $('#prevPage').prop('disabled', data.first);
                $('#nextPage').prop('disabled', data.last);
            });
        },
        error: function(err) {
            alert("권한이 없습니다!");
            window.location.href = '/admin';
        }
    });
}

function deleteMissionFunction(missionId) {
    $.ajax({
        type: "DELETE",
        url: "/api/mission/" + missionId,
        success : function(res){
            alert(missionId + "번 미션을 삭제했습니다.");
        },
        error : function(error){
            alert("권한이 없습니다");
            window.location.href = '/admin';
        }
    })
}