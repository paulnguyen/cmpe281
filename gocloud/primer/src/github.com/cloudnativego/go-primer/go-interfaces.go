package main

import "fmt"

type attacker struct {
	attackpower int
	dmgbonus    int
}

type sword struct {
	attacker
	twohanded bool
}

type gun struct {
	attacker
	bulletsremaining int
}

type chair struct {
	legcount int
	leather  bool
}

type weapon interface {
	Wield() bool
}

func (s sword) Wield() bool {
	fmt.Println("You've wielded a sword!")
	return true
}

func (g gun) Wield() bool {
	fmt.Println("You've wielded a gun!")
	return true
}

func (c chair) Wield() bool {
	fmt.Println("You've wielded a chair!! You having a bad day?")
	return true
}

func wielder(w weapon) bool {
	fmt.Println("Wielding...")
	return w.Wield()
}

func main() {
	sword1 := sword{attacker: attacker{attackpower: 1, dmgbonus: 5}, twohanded: true}

	gun1 := gun{attacker: attacker{attackpower: 10, dmgbonus: 20}, bulletsremaining: 11}

	chair1 := chair{legcount: 3, leather: true}

	fmt.Printf("Weapons: sword: %v, gun: %v\n", sword1, gun1)
	wielder(sword1)
	wielder(gun1)
	wielder(chair1)
}
