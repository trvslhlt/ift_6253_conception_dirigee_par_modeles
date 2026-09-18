mindmap ift6253 (tag STAR, tag QUESTION) {
	- class (H) {
		description: "MDE and related topics"
	    view {
			color: "#0000FF"
		}
		-> schedule <STAR> <QUESTION> (H) {
			- sessions {
				-> monday
				-> wednesday
			}
			- due_dates {
				* assignments
			}
		}
	}
	- work (M) {
		- assignments {
			-> hw1
			-> hw2
			-> course_project
		}
		- readings (L) {
			-> todo
			-> done
			- projects {
				- main <STAR> {
					-> find_partner {
						- suggest_topic <QUESTION>
						-> talk_to_someone
					}
				}
			}
		}
		- homework {
			* class
		}
		- tool_tutorials
	}
}