package spacetime

import "github.com/p2hacks2022/post-team05/internal/model"

func GetByTime(time string) ([]SpaceTime, error) {
	// dbmap初期化
	dbmap, err := model.InitDb()
	if err != nil {
		return []SpaceTime{}, err
	}
	defer dbmap.Db.Close()

	// time指定でspacetimesのデータを取得
	var spacetimes []SpaceTime
	_, err = dbmap.Select(&spacetimes, "SELECT * FROM spacetimes WHERE time=?", time)
	if err != nil {
		return []SpaceTime{}, err
	}

	return spacetimes, nil
}
