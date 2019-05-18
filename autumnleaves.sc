(
~rev2 = ScProphetRev2();
~rev2.connect;
)

(
~rev2.makeNRPNResponder(nil, {|number, value| ("set "++value++" to "++number).postln; });
)

(
s.waitForBoot({
    var barcheck = {
        | bar, expectedlength, baridx |
        // expected length is expressed in #whole notes
        var p = Panola.new(bar);
        var dur = p.totalDuration;
        if (dur != expectedlength) {
            ("Bar check at bar " ++ baridx ++ "(" ++ bar ++ ") failed. Length is " ++ dur ++ " expected length is " ++ expectedlength).postln;
        };
    };
    var melody_per_meas = ["r_4@tempo[120] r_8 g4 a_4 b-",
        "e-5_1",
        "r_4. f4_8 r g r a",
        "d5_1",
        "r_4 r_8 e-4 g b- d5_4",
        "c_2 r",
        "r_8 d4 r d e d f#_4",
        "b-4_1",
        "r_8 g4 r g a_4 b-_4",
        "e-5_1",
        "r_8 <c4 e-4> f4 g a b- c5 r",
        "d5_1",
        "r_2 r_8 e-4_8 g_8*2/3 b- d5",
        "c5_8 c r_4 r_4 r_4",
        "r_8 f#4_8 a c5 e- f e- c",
        "c#5_8 d b-4 g c5 a4 f#_4",
        "b-4_8 f# g_4 a g",
        "a4_2 <c_4. d> a_8",
        "a4_2 g_4 a_8 b-",
        "b-4_1",
        "r_2 r_4 a_8 b-_8",
        "c5_2. f4_4",
        "f5_2 <a4 d#5>",
        "<f4_2 b- d5> r2",
        "r_2 r_4 d5_8*2/3 c d",
        "e-5_2. c_4",
        "a4_2. e-5_4",
        "<b-4_2 d5> <a4 c#5>",
        "<a-4_4 c5> r_8 <d4_8*5 b->",
        "<g4_2 c5> r_8 <f4_4 b-> <e_8 f# a>",
        "<e_2 f# a> b-_4 d",
        "g_1",
        "r_1"
    ];

    var skip_measures = 0;
    var melody_joined = melody_per_meas[skip_measures..].join(" ");
    var melody_panola = Panola.new(melody_joined);
    var melody_pattern = melody_panola.asPbind();

    var accompaniment_per_meas = [
        "r_1@tempo[120]",
        "r_2 <c3_4. b-3 e-4 g4> <f3_8 a3 e-4 g-4 a4>",
        "<f3_4. a3 e-4 g-4 a4> r_8 r_2",
        "r_2 <b-2_4. g3 c4 f a> <e-3_8 b-3 d4 g>",
        "<e-3_2 b-3 d4 g> r_2",
        "r_2 <a2_4. g3 c4 e- g> <d3_8 f# c4 f>",
        "<d3_4 f# c4 f> r_4 r_2",
        "r_8 <g2_8 f3 b- d4> r_4 <g2_4. e3 b- d4> <g2_8 d3 b- d4>",
        "<g2_2 d3 b- d4> r_2",
        "r_2 <c3_4. b-3 d4 e- g> <f3_8 a d4 e4- g- a>",
        "<f3_4 a d4> r_4 r_2",
        "r_2 <b-2_8 a-3 d-4 g- b-> <g3_8 c4 f a> r_8 <e-2 b- f3 b3- d4 g>",
        "<e-2_4. b- f3 b3- d4 g> e-_8*5",
        "r_2 <a2_4. g3 c4 e- g> <d3_8 f# c4 f>",
        "<d3_4 f# c4 f> r_4 r_8 <d3_8 f# a> r_4",
        "r_8 <g2_8 d3 b-> r_4 <a2_8 g3> r_8 r_4",
        "<g2_4. f3> <f3_8 a b- d4> r_4 r_8 e-3_8",
        "a2_2 g3_4 d3_4",
        "d3_8 <f#3_4 c4> d2_8 r_2",
        "r_8 <a3_8 b-3 d4> r_8 <f#3_4. b- d4> a-2_4",
        "<a-2_4. g-3 b- c4 f> <g2_4. f3 a b- d4> r_4",
        "c3_2 <b-3_2 e-4>",
        "f2_2 <b2_2 a3>",
        "<b-2_2 g3> <e2 d3 g# d4 f#>",
        "<e-2_2 b- g3 b- d4 f> b-2_4 r_4",
        "a2_4. <g3_8*5 c4>",
        "d3_4. <f#_8*5 c4 f>",
        "<a3_2 b- d4> <f#3 a c#4>",
        "<f3_4 a- c4> r_8 <b-2_8*3 a-3> e-2_4",
        "<e-3_2 b- c4> r_4 r_8 <d3_8 f# c4>",
        "<d3_2 f# c4> r_4 r_4",
        "r_8 <g2_8 f3 b- d4> r_4 <g2_4. e3 b- d4> <g2_8 d3 b- d4>",
        "<g2_2 d3 b- d4> r_2"
    ];
    var accompaniment_joined = accompaniment_per_meas[skip_measures..].join(" ");
    var accompaniment_panola = Panola.new(accompaniment_joined);
    var accompaniment_pattern = accompaniment_panola.asPbind();

    var wiggle;
    var wiggle_pattern;
    var chaotic_wiggle;
    var chaotic_wiggle_pattern;
    var combined_pattern1 = Ppar([melody_pattern, accompaniment_pattern], 1);

    var melody_notes = melody_panola.midinotePattern.asStream.all+12;
    var melody_durations = melody_panola.durationPattern.asStream.all;
    var var1_melody_notes = [];
    var var1b_melody_notes = [];
    var var3_melody_notes = [];
    var var4_melody_notes = [];
    var var1_melody_durs = [];
    var var1b_melody_durs = [];
    var var3_melody_durs = [];
    var var4_melody_durs = [];
    var accompaniment_notes = accompaniment_panola.midinotePattern.asStream.all+12;
    var accompaniment_durations = accompaniment_panola.durationPattern.asStream.all;
    var var1_accompaniment_notes = [];
    var var1_accompaniment_durs = [];
    var var1b_accompaniment_notes = [];
    var var1b_accompaniment_durs = [];

    var l =  NrpnTable();
    var original_pattern = Ppar([
        Pbind(
            // \instrument, \default,

            \type, \midi,
            \midicmd, \noteOn,
            \midiout, ~rev2.midi_out, // must provide the MIDI target here
            \chan, 0,

            \midinote, Pseq(melody_notes),
            \dur, Pseq(melody_durations)
        ),
        Pbind(
            //\instrument, \default,

            \type, \midi,
            \midicmd, \noteOn,
            \midiout, ~rev2.midi_out, // must provide the MIDI target here
            \chan, 0,

            \midinote, Pseq(accompaniment_notes),
            \dur, Pseq(accompaniment_durations)
        ),
    ]);
    var variation1_pattern;
    var variation1b_pattern;
    var variation2_pattern;
    var variation2a_pattern;
    var var2a_melody_durs;
    var var2a_accompaniment_durs;
    var variation2c_pattern;
    var var2c_melody_durs;
    var var2c_accompaniment_durs;

    var variation3_pattern;
    var variation4_pattern;
    var all_variations;

    var expand_note = {
        | note, duration, range=6, maxdiv=3, modmult=5 |
        var resulting_notes = [];
        var resulting_durs = [];
        var divisions = 1.rrand(maxdiv);
        if (note.class == Rest) {
            resulting_notes = resulting_notes.add(note);
            resulting_durs = resulting_durs.add(duration);
        } /* else */ {
            if (divisions > 2) {
                resulting_notes = resulting_notes.add(note);
                resulting_durs = resulting_durs.add(duration/divisions);
                (divisions-2).do({
                    var mod = range.rrand(range.neg);
                    resulting_notes = resulting_notes.add(note+(mod*modmult));
                    resulting_durs = resulting_durs.add(duration/divisions);
                });
                resulting_notes = resulting_notes.add(note);
                resulting_durs = resulting_durs.add(duration/divisions);
            } {
                resulting_notes = resulting_notes.add(note);
                resulting_durs = resulting_durs.add(duration);
            };
        };

        [resulting_notes, resulting_durs]
    };

    var chordify = {
        | notes, durations, bylength=1 /*bylength = length in whole notes */|
        var measures_note = [];
        var measure_note = [];
        var measures_dur = [];
        var measure_dur = [];
        var accumulated_length = 0;
        notes.do({
            | note, idx |

            var duration = durations[idx];
            measure_note = measure_note.add(note);
            measure_dur = measure_dur.add(duration);
            accumulated_length = accumulated_length + duration;

            if ((accumulated_length >= bylength) || (idx == notes.size) ) {
                measures_note = measures_note.add(measure_note.flatten);
                measures_dur = measures_dur.add(measure_dur.sum);
                measure_note = [];
                measure_dur = [];
                accumulated_length = 0;
            };
        });
        [measures_note, measures_dur];
    };

    var partial_reverse = {
        | notes, durations, bylength=1 /*bylength = length in whole notes */|
        var measures_note = [];
        var measure_note = [];
        var measures_dur = [];
        var measure_dur = [];
        var accumulated_length = 0;
        notes.do({
            | note, idx |

            var duration = durations[idx];
            measure_note = measure_note ++ [note];
            measure_dur = measure_dur ++ duration;
            accumulated_length = accumulated_length + duration;

            if ((accumulated_length >= bylength) || (idx == notes.size) ) {
                measures_note = measures_note ++ measure_note.reverse;
                measures_dur = measures_dur ++ measure_dur;
                measure_note = [];
                measure_dur = [];
                accumulated_length = 0;
            };
        });
        [measures_note, measures_dur];
    };

    var arpeggify = {
        | notes, durations |
        var measures_note = [];
        var measures_dur = [];
        if (notes.class == Array) {
            notes.size.do({ |i|
                measures_note = measures_note ++ [notes[i]];
                measures_dur =  measures_dur ++ [(durations/(notes.size))];
            });
        } {
            measures_note = measures_note ++ [notes];
            measures_dur =  measures_dur ++ [durations];
        };

        [measures_note, measures_dur]
    };

    melody_panola.midinotePattern.asStream.all.do({
        | note, idx |
        if (note.class == Array) {
            var length = note.size;
            length.do({
                |i|
                var decorated = expand_note.(note[i], melody_durations[idx]/length, 4, 6, 1);
                var1_melody_notes = var1_melody_notes ++ decorated[0];
                var1_melody_durs = var1_melody_durs ++ decorated[1];
            });
        } /*else*/ {
            var decorated = expand_note.(note, melody_durations[idx], 4, 6, 1);
            var1_melody_notes = var1_melody_notes ++ decorated[0];
            var1_melody_durs = var1_melody_durs ++ decorated[1];
        };
    });

    melody_panola.midinotePattern.asStream.all.do({
        | note, idx |
        var decorated = arpeggify.(note, melody_durations[idx]);
        var1b_melody_notes = var1b_melody_notes ++ decorated[0];
        var1b_melody_durs = var1b_melody_durs ++ decorated[1];
    });

    melody_panola.midinotePattern.asStream.all.reverse.do({
        | note, idx |
        if (note.class == Array) {
            var length = note.size;
            length.do({
                |i|
                var decorated = expand_note.(note[i], melody_durations[idx]/length, 4, 6, 1);
                var4_melody_notes = var4_melody_notes ++ decorated[0];
                var4_melody_durs = var4_melody_durs ++ decorated[1];
            });
        } /*else*/ {
            var decorated = expand_note.(note, melody_durations[idx], 4, 6, 1);
            var4_melody_notes = var4_melody_notes ++ decorated[0];
            var4_melody_durs = var4_melody_durs ++ decorated[1];
        };
    });

    accompaniment_panola.midinotePattern.asStream.all.do({
        | note, idx |
        if (note.class == Array) {
            var length = note.size;
            length.do({
                |i|
                var decorated = expand_note.(note[i], accompaniment_durations[idx]/length, 3, 3, 1);
                var1_accompaniment_notes = var1_accompaniment_notes ++ decorated[0];
                var1_accompaniment_durs = var1_accompaniment_durs ++ decorated[1];
            });
        } /*else*/ {
            var decorated = expand_note.(note, accompaniment_durations[idx], 3, 3, 1);
            var1_accompaniment_notes = var1_accompaniment_notes ++ decorated[0];
            var1_accompaniment_durs = var1_accompaniment_durs ++ decorated[1];
        };
    });

    accompaniment_panola.midinotePattern.asStream.all.do({
        | note, idx |
        var decorated = arpeggify.(note, accompaniment_durations[idx]);
        var1b_accompaniment_notes = var1b_accompaniment_notes ++ decorated[0];
        var1b_accompaniment_durs = var1b_accompaniment_durs ++ decorated[1];
    });

    // F1 P22 (PLUCKY/WASHY)
    // F3 P50 (FLOOTY)
    // F1 P48 (WOOD ORGAN)/F3 P87 (ORGAN/CLEARER)
    // F1 P69 (SOLO XTREME)/F2 P82 (SOLO XTREME SOFTER)
    // F1 P79 (modwheel,CRYSTALS)
    // F3 P66 (SWING)
    // F3 P71 (CAPRICIOUS)
    // F3 P100 (SPACY/WASHY)
    // F3 P103 (SPACY)
    // F3 112 (SAD)
    // F3 120 (XTREME CLEAR)
    // F3 P41 (EXTREME CRYSTALS)
    // F2 52 (XTREME NOT CLEAR)
    // F2 76 (XTREME MOSTLY BASS)
    // F4 P15 (COMPUTER)
    variation1_pattern = Ppar([
        Pbind(
            //\instrument, \default,

            \type, \midi,
            \midicmd, \noteOn,
            \midiout, ~rev2.midi_out, // must provide the MIDI target here
            \chan, 0,

            \midinote, Pseq(var1_melody_notes),
            \dur, Pseq(var1_melody_durs)),
        Pbind(
            //\instrument, \default,

            \type, \midi,
            \midicmd, \noteOn,
            \midiout, ~rev2.midi_out, // must provide the MIDI target here
            \chan, 0,

            \midinote, Pseq(var1_accompaniment_notes),
            \dur, Pseq(var1_accompaniment_durs))
        ]);

    variation1b_pattern = Ppar([
        Pbind(
            //\instrument, \default,

            \type, \midi,
            \midicmd, \noteOn,
            \midiout, ~rev2.midi_out, // must provide the MIDI target here
            \chan, 0,
            \amp, 0.9,
            \midinote, Pseq(var1b_melody_notes),
            \dur, Pseq(var1b_melody_durs*2)),
        Pbind(
            //\instrument, \default,

            \type, \midi,
            \midicmd, \noteOn,
            \midiout, ~rev2.midi_out, // must provide the MIDI target here
            \amp, 0.9,
            \chan, 0,

            \midinote, Pseq(var1b_accompaniment_notes),
            \dur, Pseq(var1b_accompaniment_durs*2))
    ]);

    var2a_melody_durs = (chordify.(melody_panola.midinotePattern.asStream.all.reverse, melody_panola.durationPattern.asStream.all,1)[1]*6);
    var2a_accompaniment_durs = (chordify.(accompaniment_panola.midinotePattern.asStream.all+12,accompaniment_panola.durationPattern.asStream.all,1)[1]*3);
    variation2a_pattern = Ppar([
        Pbind(
            //\instrument, \default,

            \type, \midi,
            \midicmd, \noteOn,
            \midiout, ~rev2.midi_out, // must provide the MIDI target here
            \chan, 0,

            \midinote, Pseq(chordify.(melody_panola.midinotePattern.asStream.all.reverse+12, melody_panola.durationPattern.asStream.all,1)[0]),
            \dur, Pseq(var2a_melody_durs),
            \amp, Pbrown(0.56,0.7,0.05),
        ),
        Pbind(
            // \instrument, \default,

            \type, \midi,
            \midicmd, \noteOn,
            \midiout, ~rev2.midi_out, // must provide the MIDI target here
            \chan, 0,

            \midinote, Pseq(chordify.(accompaniment_panola.midinotePattern.asStream.all+12,accompaniment_panola.durationPattern.asStream.all,1)[0]),
            \dur, Pseq(var2a_accompaniment_durs),
        ),
    ]);

    var2c_melody_durs = (chordify.(melody_panola.midinotePattern.asStream.all.reverse, melody_panola.durationPattern.asStream.all,1)[1]*3);
    var2c_accompaniment_durs = (chordify.(accompaniment_panola.midinotePattern.asStream.all,accompaniment_panola.durationPattern.asStream.all,1)[1]*1.5);
    variation2c_pattern = Ppar([
        Pbind(
            //\instrument, \default,

            \type, \midi,
            \midicmd, \noteOn,
            \midiout, ~rev2.midi_out, // must provide the MIDI target here
            \chan, 0,

            \midinote, Pseq(chordify.(melody_panola.midinotePattern.asStream.all.reverse+12, melody_panola.durationPattern.asStream.all,1)[0]),
            \dur, Pseq(var2c_melody_durs),
            \amp, Pbrown(0.56, 0.9, 0.05),
        ),
        Pbind(
            // \instrument, \default,

            \type, \midi,
            \midicmd, \noteOn,
            \midiout, ~rev2.midi_out, // must provide the MIDI target here
            \chan, 0,

            \midinote, Pseq(chordify.(accompaniment_panola.midinotePattern.asStream.all+12,accompaniment_panola.durationPattern.asStream.all,1)[0],2),
            \dur, Pseq(var2c_accompaniment_durs),
        ),
    ]);

    variation2_pattern = Ppar([
        Pbind(
            //\instrument, \default,

            \type, \midi,
            \midicmd, \noteOn,
            \midiout, ~rev2.midi_out, // must provide the MIDI target here
            \chan, 0,

            \midinote, Pseq(melody_panola.midinotePattern.asStream.all.reverse+12),
            \dur, Pseq(melody_panola.durationPattern.asStream.all),
            \amp, 0.56,
        ),
        Pbind(
            // \instrument, \default,

            \type, \midi,
            \midicmd, \noteOn,
            \midiout, ~rev2.midi_out, // must provide the MIDI target here
            \chan, 0,

            \midinote, Pseq(chordify.(accompaniment_panola.midinotePattern.asStream.all+12,accompaniment_panola.durationPattern.asStream.all,1)[0]),
            \dur, Pseq(chordify.(accompaniment_panola.midinotePattern.asStream.all+12,accompaniment_panola.durationPattern.asStream.all,1)[1]),
        ),
    ]);

    variation3_pattern = Ppar([
        Pbind(
            //\instrument, \default,

            \type, \midi,
            \midicmd, \noteOn,
            \midiout, ~rev2.midi_out, // must provide the MIDI target here
            \chan, 0,

            //\midinote, Pseq(partial_reverse.(melody_panola.midinotePattern.asStream.all+12,melody_panola.durationPattern.asStream.all)[0]),
            //\dur, Pseq(melody_panola.durationPattern.asStream.all),
            \midinote, Pseq(melody_notes),
            \dur, Pseq(melody_durations),
            \amp, 0.9,
        ),
        Pbind(
            //\instrument, \default,

            \type, \midi,
            \midicmd, \noteOn,
            \midiout, ~rev2.midi_out, // must provide the MIDI target here
            \chan, 0,

            \midinote, Pseq(chordify.(accompaniment_panola.midinotePattern.asStream.all+12,accompaniment_panola.durationPattern.asStream.all,1)[0].dup(3).flatten(1)),
            \dur, Pseq(chordify.(accompaniment_panola.midinotePattern.asStream.all,accompaniment_panola.durationPattern.asStream.all,1)[1].dup(3).flatten(1)/3),
            \amp, 0.3,
        ),
    ]);

    variation4_pattern = Ppar([
        Pbind(
            //\instrument, \default,

            \type, \midi,
            \midicmd, \noteOn,
            \midiout, ~rev2.midi_out, // must provide the MIDI target here
            \chan, 0,

            \midinote, Pseq(var4_melody_notes),
            \dur, Pseq(melody_panola.durationPattern.asStream.all),
            \amp, 0.9
        ),
        Pbind(
            //\instrument, \default,

            \type, \midi,
            \midicmd, \noteOn,
            \midiout, ~rev2.midi_out, // must provide the MIDI target here
            \chan, 0,

            \midinote, Pseq(chordify.(accompaniment_panola.midinotePattern.asStream.all+12,accompaniment_panola.durationPattern.asStream.all,1)[0].dup(2).flatten(1)),
            \dur, Pseq(chordify.(accompaniment_panola.midinotePattern.asStream.all,accompaniment_panola.durationPattern.asStream.all,1)[1].dup(2).flatten(1)/2),
            \amp, 0.9
        ),
    ]);

    wiggle = { |x| sin(2*pi*0.1*x).linlin(-1, 1, (8192-600), (8192+600)); };
    wiggle_pattern = Pbind(
        \dev, Ptime().collect(wiggle),
        \send, Pfunc({|ev| ~rev2.midi_out.bend(0, ev[\dev]); }),
        \dur, 0.2,
        \amp, 0,
    );
    chaotic_wiggle = { |x| sin(2*pi*0.3*x).linlin(-1, 1, (8192-5000), (8192+5000)); };
    chaotic_wiggle_pattern = Pbind(
        \dev, Phenon().linlin(0,1,8192-5000,8192+5000),
        \send, Pfunc({|ev| ~rev2.midi_out.bend(0, ev[\dev][0]); }),
        \dur, 0.2,
        \amp, 0,
    );


    all_variations = Pseq([
        Pbind(\send, Pfunc({~rev2.select_patch_by_id("F1", "P3"); ~rev2.sendNRPN(l.str2num(\UNISON_OFFON,"B").debug("nrpn"), 0); nil})),
        original_pattern,

        Pbind(\send, Pfunc({~rev2.select_patch_by_id("F3", "P5"); nil})),
        variation2_pattern,

        ////Pbind(\send, Pfunc({~rev2.select_patch_by_id("F3", "P100"); nil})),
        ////variation2_pattern,

        Pbind(\send, Pfunc({~rev2.select_patch_by_id("F3", "P11"); nil})),
        variation3_pattern,

        Pbind(\send, Pfunc({~rev2.select_patch_by_id("F3", "P20"); nil})),
        Ppar([
            Pseq([
                Pfindur((var2a_accompaniment_durs.sum).max(var2a_melody_durs.sum)/2,
                     wiggle_pattern),
                Pfunc({~rev2.midi_out.bend(0, 8192); nil})]),
            Pfindur((var2a_accompaniment_durs.sum).max(var2a_melody_durs.sum)/2,
                variation2a_pattern)]),

        Pbind(\send, Pfunc({~rev2.select_patch_by_id("F1", "P79"); nil})),
        variation3_pattern,

        Pbind(\send, Pfunc({~rev2.select_patch_by_id("F1", "P86"); nil})),
        variation4_pattern,

        //Pbind(\send, Pfunc({~rev2.select_patch_by_id("F1", "P48"); nil})),
        //Ppar([
        //    Pseq([
        //        Pfindur((var1_accompaniment_durs.sum).max(var1_melody_durs.sum),
        //            wiggle_pattern),
        //        Pfunc({~rev2.midi_out.bend(0, 8192); nil})]),
        //    Pfindur((var1_accompaniment_durs.sum).max(var1_melody_durs.sum), variation1_pattern)]),
        //Pbind(\send, Pfunc({~rev2.select_patch_by_id("F1", "P22"); nil})),
        //variation1_pattern,

        Pbind(\send, Pfunc({~rev2.select_patch_by_id("F2", "P74"); nil})),
        variation2c_pattern,

        Pbind(\send, Pfunc({~rev2.select_patch_by_id("F3", "P66"); nil})),
        variation1b_pattern,

        Pbind(\send, Pfunc({~rev2.select_patch_by_id("F2", "P61"); nil})),
        variation1_pattern,

        Pbind(\send, Pfunc({~rev2.select_patch_by_id("F2", "P81"); nil})),
        variation4_pattern,

        Pbind(\send, Pfunc({~rev2.select_patch_by_id("F2", "P128"); nil})),
        variation1_pattern,

        Pbind(\send, Pfunc({~rev2.select_patch_by_id("F3", "P73"); nil})),
        Ppar([
            Pseq([
                Pfindur((var2c_accompaniment_durs.sum).max(var2c_melody_durs.sum)/2,
                     chaotic_wiggle_pattern),
                Pfunc({~rev2.midi_out.bend(0, 8192); nil})]),
            Pfindur((var2c_accompaniment_durs.sum).max(var2c_melody_durs.sum)/2,
                variation2c_pattern)]),

        Pbind(\send, Pfunc({~rev2.select_patch_by_id("F3", "P28"); ~rev2.sendNRPN(l.str2num('ABMODE'), 0); nil})),
        Padd(\midinote, Pfunc({-24}), variation2c_pattern),

        Pbind(\send, Pfunc({~rev2.select_patch_by_id("F3", "P128"); ~rev2.sendNRPN(l.str2num(\UNISON_OFFON), 0); nil})),
        variation1_pattern,

        Pbind(\send, Pfunc({~rev2.select_patch_by_id("U1", "P1"); ~rev2.sendNRPN(l.str2num(\UNISON_OFFON), 0); ~rev2.sendNRPN(l.str2num(\UNISON_OFFON, "B"), 0); nil})),
        Padd(\midinote, Pfunc({-24}), original_pattern),

        Pbind(\send, Pfunc({~rev2.select_patch_by_id("F2", "P106"); ~rev2.sendNRPN(l.str2num(\UNISON_OFFON), 0); nil})),
        variation1_pattern,

    ]);

    all_variations.play;


    melody_per_meas.do({|bar,baridx|
        barcheck.(bar, 1, baridx);
    });
    accompaniment_per_meas.do({|bar,baridx|
        barcheck.(bar, 1, baridx);
    });

});
)