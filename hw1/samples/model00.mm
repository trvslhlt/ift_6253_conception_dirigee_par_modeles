mindmap mde_course (tag STAR, tag QUESTION) {
	- class (H) {
		description: "text description of topic"
			        view {
			color: "#0000FF"
		}
		-> attendance <STAR>           <QUESTION> (H)
		- active_learning {
			- take_notes
			- clarify_with_chat
		}
	}
	- work (M) {
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
		- homework *class
		- tool_tutorials
	}
}