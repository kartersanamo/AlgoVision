/algo
├── help [category]
├── wand
├── region
│   ├── pos1 [set|clear|info]
│   ├── pos2 [set|clear|info]
│   ├── info
│   ├── clear
│   ├── expand <amount> [direction]
│   ├── contract <amount> [direction]
│   └── shift <amount> <direction>
├── settings
│   ├── list [global|personal]
│   ├── get <key>
│   ├── set <key> <value> [global|personal]
│   ├── reset [global|personal|key]
│   ├── export <filename>
│   └── import <filename>
├── visualize <category> <algorithm> [options...]
│   ├── sorting <algorithm> [data_pattern] [size]
│   ├── searching <algorithm> <target> [data_pattern] [size]
│   ├── graph <algorithm> [start] [end]
│   ├── tree <algorithm> [operation] [value]
│   ├── pathfinding <algorithm> [start_x] [start_z] [end_x] [end_z]
│   ├── dynamic <algorithm> [parameters...]
│   ├── greedy <algorithm> [parameters...]
│   └── backtracking <algorithm> [parameters...]
├── control
│   ├── pause [id]
│   ├── resume [id]
│   ├── stop [id]
│   ├── step [id] [steps]
│   ├── speed <multiplier> [id]
│   ├── skip [id]
│   ├── restart [id]
│   └── list
├── preset
│   ├── save <name>
│   ├── load <name>
│   ├── list
│   ├── delete <name>
│   ├── info <name>
│   ├── export <name> <filename>
│   └── import <filename> [name]
├── data
│   ├── generate <type> [size] [parameters...]
│   ├── load <pattern>
│   ├── custom <values...>
│   ├── info
│   ├── clear
│   └── preview
└── stats
├── show [id]
├── history [limit]
├── compare <id1> <id2>
├── reset
├── export [filename]
└── leaderboard <algorithm> [limit]