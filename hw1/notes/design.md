# Design

## Test Model

To be used to test the grammar and validations. Can be modified as needed, not a fixed spec.

```
mindmap mde_course (tag STAR, tag QUESTION) {
    - class (H) {
        description: "text description of topic"
        view {
            color: "#0000FF"
        }
        -> attendance <STAR> <QUESTION> (H)
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
        - homework
        - tool_tutorials
    }
}   
```

## Sketching

Brainstorming and iterating with docs, tooling, and AI assistants (for critique, not design or implementation)

### Tooling
- Explore visualizing meta/model with [Sirius](https://eclipse.dev/sirius/overview.html) (Eugenia has been discontinued)

### Basic Description
- a forest of trees

### Graphical Features
Behaviors we would like to see supported in a graphical representation of a mindmap
- add nodes
- remove nodes
    - removes entire subtree
- assign nodes to new parents
    - they bring their subtree with them
- reposition nodes
    - set position (x, y)

### Types
- MindMap
    - roots: { Node 0..* }
- Topic
    - name: string

    - color: string // hex color
    - children: { Node 0..* }

### Constraints
- node names must be unique

### Sample Textual MindMap
This is not a spec, just what I am imagining at the moment.

- revisiting features in light of "marker" example
    - tags: can be any all caps string, indicated in model be "<TAG>" 
    - priority: L, M, H (default none), indicated as "(L)", "(M)", or "(H)"
    - '>' is a child topic at the same level
    - '>>' is a child topic one level down (sub-topic)
        - only 3 topic levels supported


```
mindmap mde_course (tag STAR, tag QUESTION) {
    - class (H) {
        description: "text description of topic"
        view {
            color: "#0000FF"
        }
        -> attendance <STAR> <QUESTION> (H)
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
        - homework
        - tool_tutorials
    }
}   
```
