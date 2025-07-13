var currentPage = 0;
var pageSize = 10;

$(document).ready(function() {
    getMembers(currentPage);

    $('#prevPage').click(function() {
        if (currentPage > 0) {
            getMembers(currentPage - 1);
        }
    });

    $('#nextPage').click(function() {
        getMembers(currentPage + 1);
    });
});

function getMembers(page){
    $.ajax({
        url: '/api/admin/user' + '?page=' + page + '&size=' + pageSize,
        method: 'GET',
        success: function(data) {
            var contents = data.content;
            var tbody = $('#member-tbody');
            tbody.empty(); // 초기화

            // 데이터에 따라 테이블 행을 생성
            contents.forEach(function(member) {
                // 한 행의 내용
                var row = `
                        <tr>
                            <td>${member.userId}</td>
                            <td>${member.email}</td>
                            <td>${member.userName}</td>
                            <td>${member.role}</td>
                            <td>
                                <a href="#" onclick="grantFunction(${member.userId})">권한변경</a>
                            </td>
                            <td>
                                <a href="#" onclick="deleteMemberFunction(${member.userId})">삭제</a>
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

function deleteMemberFunction(id){
    $.ajax({
        type: "DELETE",
        url: "/api/admin/user/" + id,
        success : function(res){
            alert(id + "번 유저가 삭제되었습니다.");
            getMembers(currentPage);
        },
        error : function(error){
            alert("권한이 없습니다");
            window.location.href = '/admin';
        }
    })
}

function grantFunction(id) {
    $.ajax({
        type: "POST",
        url: "/api/admin/user/" + id + "/grant",
        success : function(res){
            alert("유저 ID : " + id + "의 권한이 변경되었습니다.");
            getMembers(currentPage);
        },
        error : function(error){
            alert("권한이 없습니다");
            window.location.href = '/admin';
        }
    })
}