# TastyJapan-Server

## Code Convention
- [Kotlin Style Guide](https://kotlinlang.org/docs/reference/coding-conventions.html)
- Reformat Code
  - MacOS: `Ctrl + Opt + L`
  - Windows/Linux: `Ctrl + Alt + L`
- Optimize Imports
  - MacOS: `Ctrl + Opt + O`
  - Windows/Linux: `Ctrl + Alt + O`

## Commit Message Convention
- feat (feature)
- fix (bug fix)
- docs (documentation)
- style (formatting, missing semi colons, …)
- refactor
- test (when adding missing tests)
- chore (maintain)

## ERD
TODO

## API Specs
TODO

## 기능 목록
> 살아 있는 기능 목록을 만들자! 언제든지 수정해도 괜찮다.
### Member
- [ ] 사용자는 회원가입을 할 수 있다.
- [ ] 사용자는 구글 로그인을 할 수 있다.
- [ ] 사용자는 로그아웃을 할 수 있다.
- [ ] 사용자의 필드는 이름, 이메일, 사진, 역할이다.

### Restaurant
- [ ] 식당을 도시 별, 메뉴 종류, 위도, 경도 값으로 조회할 수 있다.
- [ ] 식당의 상세 정보를 조회할 수 있다.
- [ ] Pagination을 적용하여 10개까지 반환한다.
- [ ] 식당을 한 곳 추천한다. 블로그 리뷰 개수 100개 이상 또는 평점 4.0 이상인 곳 중 랜덤으로 반환한다.
- [ ] 식당의 필드는 이름, 주소, 도시(Enum), 위도, 경도, 평점, 한줄요약이다.
- [ ] 식당 사진 Class는 따로 생성한다.

### Review
- [ ] 사용자는 식당에 대한 리뷰를 조회할 수 있다.
- [ ] 사용자는 식당에 대한 리뷰를 수정할 수 있다.
- [ ] 사용자는 식당에 대한 리뷰를 삭제할 수 있다.
- [ ] 사용자는 식당에 대한 리뷰를 생성할 수 있다.
- [ ] 블로그 리뷰는 url만 포함한다.
- [ ] 외부 리뷰 Class는 따로 생성하고 출처를 표시한다.


### Group
- [ ] 사용자는 그룹을 생성할 수 있다.
- [ ] 사용자는 그룹의 제목을 수정할 수 있다.
- [ ] 사용자는 그룹을 조회할 수 있다.
- [ ] 사용자는 그룹을 삭제할 수 있다.
- [ ] 그룹 필드는 제목, 생성일, 수정일이다.

### Menu
- [ ] 메뉴탭에서 식당의 메뉴를 조회할 수 있다.
- [ ] 대표적인 메뉴별로 메뉴가 포함된 식당을 조회한다.
- [ ] 메뉴의 필드는 이름, 가격, 메뉴종류, 사진들이다.