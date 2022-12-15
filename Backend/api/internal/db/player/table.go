package player

// Status
// 1: プレイ中
// 2: 捕まった
// 3: 勝利
// 4: 鬼
type Player struct {
	Id     int `db:"id"`
	Status int `db:"status"`
}
