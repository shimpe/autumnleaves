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
    var combined_pattern1 = Ppar([melody_pattern, accompaniment_pattern], 1);
    var melody_notes = melody_panola.midinotePattern.asStream.all+12;
    var melody_durations = melody_panola.durationPattern.asStream.all;
    var accompaniment_notes = accompaniment_panola.midinotePattern.asStream.all+12;
    var accompaniment_durations = accompaniment_panola.durationPattern.asStream.all;

    var l =  NrpnTable();

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

    var expand_notes_331 = {
        | notes, durations |
        var ns = [];
        var ds = [];
        notes.do({
            | note, idx |
            if (note.class == Array) {
                var length = note.size;
                length.do({
                    |i|
                    var decorated = expand_note.(note[i], durations[idx]/length, 3, 3, 1);
                    ns = ns ++ decorated[0];
                    ds = ds ++ decorated[1];
                });
            } /*else*/ {
                var decorated = expand_note.(note, durations[idx], 3, 3, 1);
                ns = ns ++ decorated[0];
                ds = ds ++ decorated[1];
            };
        });

        [ns, ds]
    };

    var expand_notes_461 = {
        | notes, durations |
        var ns = [];
        var ds = [];
        notes.do({
            | note, idx |
            if (note.class == Array) {
                var length = note.size;
                length.do({
                    |i|
                    var decorated = expand_note.(note[i], durations[idx]/length, 4, 6, 1);
                    ns = ns ++ decorated[0];
                    ds = ds ++ decorated[1];
                });
            } /*else*/ {
                var decorated = expand_note.(note, durations[idx], 4, 6, 1);
                ns = ns ++ decorated[0];
                ds = ds ++ decorated[1];
            };
        });

        [ns, ds]
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
    var chordify_per_meas = { |notes, durations| chordify.(notes, durations, 1); };
    var keep_original = { | notes, durations | [notes, durations]; };
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
    var partial_reverse_by_meas = { |notes, durations| partial_reverse.(notes, durations, 1); };

    var arpeggify = {
        | notes, durations |
        var ns = [];
        var ds = [];
        notes.do({
            | note, idx |
            if (note.class == Array) {
                note.size.do({ |i|
                    ns = ns ++ [note[i]];
                    ds = ds ++ [(durations[idx]/(note.size))];
                });
            } {
                ns = ns ++ [note];
                ds = ds ++ [durations[idx]];
            };
        });

        [ns, ds]
    };

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
    var pattern_compiler = {
        | mel_notes, mel_durs, meldur_transformer, mel_tempo_scale, accomp_notes, accomp_durs, accompdur_transformer, accomp_tempo_scale, mel_amp_pat=nil, accomp_amp_pat=nil, mel_chan=0, dur_chan=0 |

        var mel_final_durations = (meldur_transformer.(mel_notes, mel_durs)[1])*mel_tempo_scale;
        var mel_final_notes = meldur_transformer.(mel_notes, mel_durs)[0];
        var accomp_final_durations = (accompdur_transformer.(accomp_notes, accomp_durs)[1])*accomp_tempo_scale;
        var accomp_final_notes = accompdur_transformer.(accomp_notes, accomp_durs)[0];

        if (mel_amp_pat.isNil) { mel_amp_pat = Pseq([0.5], inf); };
        if (accomp_amp_pat.isNil) { accomp_amp_pat = Pseq([0.5], inf); };

        (
            \pat : Ppar([
                Pbind(
                    //\instrument, \default,

                    \type, \midi,
                    \midicmd, \noteOn,
                    \midiout, ~rev2.midi_out, // must provide the MIDI target here
                    \chan, mel_chan,

                    \midinote, Pseq(mel_final_notes),
                    \dur, Pseq(mel_final_durations),
                    \amp, mel_amp_pat,
                ),
                Pbind(
                    // \instrument, \default,

                    \type, \midi,
                    \midicmd, \noteOn,
                    \midiout, ~rev2.midi_out, // must provide the MIDI target here
                    \chan, dur_chan,

                    \midinote, Pseq(accomp_final_notes),
                    \dur, Pseq(accomp_final_durations),
                    \amp, accomp_amp_pat
                ),
            ]),
            \mel_durs : mel_final_durations,
            \mel_notes: mel_final_notes,
            \accomp_durs: accomp_final_durations,
            \accomp_notes: accomp_final_notes
        )
    };

    var original_pattern = pattern_compiler.(
        mel_notes: melody_panola.midinotePattern.asStream.all + 12,
        mel_durs: melody_panola.durationPattern.asStream.all,
        meldur_transformer: keep_original,
        mel_tempo_scale: 1,
        accomp_notes: accompaniment_panola.midinotePattern.asStream.all+12,
        accomp_durs: accompaniment_panola.durationPattern.asStream.all,
        accompdur_transformer: keep_original,
        accomp_tempo_scale: 1,
    );

    var variation1_pattern = pattern_compiler.(
        mel_notes: melody_panola.midinotePattern.asStream.all,
        mel_durs: melody_panola.durationPattern.asStream.all,
        meldur_transformer: expand_notes_461,
        mel_tempo_scale: 1,
        accomp_notes: accompaniment_panola.midinotePattern.asStream.all,
        accomp_durs: accompaniment_panola.durationPattern.asStream.all,
        accompdur_transformer: expand_notes_331,
        accomp_tempo_scale:1
    );

    var variation1b_pattern = pattern_compiler.(
        mel_notes: melody_panola.midinotePattern.asStream.all,
        mel_durs: melody_panola.durationPattern.asStream.all,
        meldur_transformer: arpeggify,
        mel_tempo_scale: 1,
        accomp_notes: accompaniment_panola.midinotePattern.asStream.all,
        accomp_durs: accompaniment_panola.durationPattern.asStream.all,
        accompdur_transformer: arpeggify,
        accomp_tempo_scale: 1,
    );

    var variation2a_pattern = pattern_compiler.(
        mel_notes: melody_panola.midinotePattern.asStream.all.reverse + 12,
        mel_durs: melody_panola.durationPattern.asStream.all,
        meldur_transformer: chordify_per_meas,
        mel_tempo_scale: 6,
        accomp_notes: accompaniment_panola.midinotePattern.asStream.all+12,
        accomp_durs: accompaniment_panola.durationPattern.asStream.all,
        accompdur_transformer: chordify_per_meas,
        accomp_tempo_scale: 3,
        mel_amp_pat: Pbrown(0.56,0.7,0.05),
    );

    var variation2c_pattern = pattern_compiler.(
        mel_notes: melody_panola.midinotePattern.asStream.all.reverse+12,
        mel_durs: melody_panola.durationPattern.asStream.all,
        meldur_transformer: chordify_per_meas,
        mel_tempo_scale: 3,
        accomp_notes: accompaniment_panola.midinotePattern.asStream.all+12,
        accomp_durs: accompaniment_panola.durationPattern.asStream.all,
        accompdur_transformer: chordify_per_meas,
        accomp_tempo_scale: 1.5,
        mel_amp_pat: Pbrown(0.56, 0.9, 0.05),
    );

    var variation2_pattern = pattern_compiler.(
        mel_notes: melody_panola.midinotePattern.asStream.all+12,
        mel_durs: melody_panola.durationPattern.asStream.all,
        meldur_transformer: keep_original,
        mel_tempo_scale: 1,
        accomp_notes: accompaniment_panola.midinotePattern.asStream.all+12,
        accomp_durs: accompaniment_panola.durationPattern.asStream.all,
        accomp_tempo_scale: 1,
        accompdur_transformer: chordify_per_meas,
        mel_amp_pat: Pseq([0.56], inf),
    );

    var variation3_pattern = pattern_compiler.(
        mel_notes: melody_notes,
        mel_durs: melody_durations,
        meldur_transformer: keep_original,
        mel_tempo_scale: 1,
        accomp_notes: (accompaniment_panola.midinotePattern.asStream.all+12).dup(3).flatten(1),
        accomp_durs: accompaniment_panola.durationPattern.asStream.all.dup(3).flatten(1),
        accomp_tempo_scale: 0.33333,
        accompdur_transformer: chordify_per_meas,
        mel_amp_pat: Pseq([0.9], inf),
        accomp_amp_pat: Pseq([0.3], inf)
    );

    var variation4_pattern = pattern_compiler.(
        mel_notes: melody_panola.midinotePattern.asStream.all.reverse,
        mel_durs: melody_panola.durationPattern.asStream.all,
        meldur_transformer: expand_notes_461,
        mel_tempo_scale: 1,
        accomp_notes: (accompaniment_panola.midinotePattern.asStream.all+12).dup(2).flatten(1),
        accomp_durs: (accompaniment_panola.durationPattern.asStream.all).dup(2).flatten(1),
        accompdur_transformer: chordify_per_meas,
        accomp_tempo_scale: 0.5,
        mel_amp_pat: Pseq([0.9], inf),
        accomp_amp_pat: Pseq([0.9], inf),
    );

    var wiggle = { |x| sin(2*pi*0.1*x).linlin(-1, 1, (8192-600), (8192+600)); };
    var wiggle_pattern = Pbind(
        \dev, Ptime().collect(wiggle),
        \send, Pfunc({|ev| ~rev2.midi_out.bend(0, ev[\dev]); }),
        \dur, 0.2,
        \amp, 0,
    );
    var chaotic_wiggle = { |x| sin(2*pi*0.3*x).linlin(-1, 1, (8192-5000), (8192+5000)); };
    var chaotic_wiggle_pattern = Pbind(
        \dev, Phenon().linlin(0,1,8192-5000,8192+5000),
        \send, Pfunc({|ev| ~rev2.midi_out.bend(0, ev[\dev][0]); }),
        \dur, 0.2,
        \amp, 0,
    );

    var all_variations = Pseq([

        Pbind(\send, Pfunc({
            ~rev2.select_patch_by_id("F1", "P3");
            ~rev2.sendNRPN(l.str2num(\UNISON_OFFON,"B"), 0);
            nil})),
        original_pattern[\pat],

        Pbind(\send, Pfunc({~rev2.select_patch_by_id("F3", "P5"); nil})),
        variation2_pattern[\pat],

        Pbind(\send, Pfunc({~rev2.select_patch_by_id("F3", "P11"); ~rev2.sendNRPN(l.str2num(\LPF_CUTOFF), 79); nil})),
        variation3_pattern[\pat],

        Pbind(\send, Pfunc({~rev2.select_patch_by_id("F3", "P20"); nil})),
        Ppar([
            Pseq([
                Pfindur((variation2a_pattern[\accomp_durs].sum).max(variation2a_pattern[\mel_durs].sum)/2,
                    wiggle_pattern),
                Pfunc({~rev2.midi_out.bend(0, 8192); nil})]),
            Pfindur((variation2a_pattern[\accomp_durs].sum).max(variation2a_pattern[\mel_durs].sum)/2,
                variation2a_pattern[\pat])]),

        Pbind(\send, Pfunc({
            ~rev2.select_patch_by_id("F1", "P79");
            ~rev2.sendNRPN(l.str2num(\LPF_CUTOFF), 25);
            ~rev2.sendNRPN(l.str2num(\LPF_RESONANCE), 10);
            ~rev2.sendNRPN(l.str2num(\LPF_ENV_AMT), 71 + 127);
            nil})),
        variation3_pattern[\pat],

        Pbind(\send, Pfunc({~rev2.select_patch_by_id("F1", "P86"); nil})),
        variation4_pattern[\pat],

        Pbind(\send, Pfunc({~rev2.select_patch_by_id("F2", "P74"); nil})),
        Pfindur((variation2c_pattern[\accomp_durs].sum).max(variation2c_pattern[\mel_durs].sum)/2,
            variation2c_pattern[\pat]),

        Pbind(\send, Pfunc({~rev2.select_patch_by_id("F3", "P66"); ~rev2.sendNRPN(l.str2num(\LPF_CUTOFF), 37); nil})),
        variation1b_pattern[\pat],

        Pbind(\send, Pfunc({~rev2.select_patch_by_id("F2", "P61"); nil})),
        variation1_pattern[\pat],

        Pbind(\send, Pfunc({~rev2.select_patch_by_id("F2", "P81"); nil})),
        variation4_pattern[\pat],

        Pbind(\send, Pfunc({
            ~rev2.select_patch_by_id("F2", "P128");
            ~rev2.sendNRPN(l.str2num(\LPF_CUTOFF), 52);
            ~rev2.sendNRPN(l.str2num(\LPF_RESONANCE), 127);
            ~rev2.sendNRPN(l.str2num(\UNISON_OFFON), 0);
            ~rev2.sendNRPN(l.str2num(\UNISON_OFFON,"B"), 0);
            nil})),
        variation1_pattern[\pat],

        Pbind(\send, Pfunc({~rev2.select_patch_by_id("F3", "P73"); nil})),
        Ppar([
            Pseq([
                Pfindur((variation2c_pattern[\accomp_durs].sum).max(variation2c_pattern[\mel_durs].sum)/2,
                    chaotic_wiggle_pattern),
                Pfunc({~rev2.midi_out.bend(0, 8192); nil})]),
            Pfindur((variation2c_pattern[\accomp_durs].sum).max(variation2c_pattern[\mel_durs].sum)/2,
                variation2c_pattern[\pat])]),

        Pbind(\send, Pfunc({~rev2.select_patch_by_id("F3", "P28"); ~rev2.sendNRPN(l.str2num('ABMODE'), 0); nil})),
        Padd(\midinote, Pfunc({-24}), variation2c_pattern[\pat]),

        Pbind(\send, Pfunc({~rev2.select_patch_by_id("F3", "P128"); ~rev2.sendNRPN(l.str2num(\UNISON_OFFON), 0); nil})),
        variation1_pattern[\pat],

        Pbind(\send, Pfunc({~rev2.select_patch_by_id("U1", "P1");
            ~rev2.sendNRPN(l.str2num(\UNISON_OFFON), 0);
            ~rev2.sendNRPN(l.str2num(\UNISON_OFFON, "B"), 0);
            ~rev2.sendNRPN(l.str2num(\LPF_RESONANCE), 127);
            nil})),
        Padd(\midinote, Pfunc({-18}), original_pattern),

        Pbind(\send, Pfunc({
            ~rev2.select_patch_by_id("F2", "P106");
            ~rev2.sendNRPN(l.str2num(\UNISON_OFFON), 0);
            ~rev2.sendNRPN(l.str2num(\LPF_CUTOFF), 75);
            ~rev2.sendNRPN(l.str2num(\LPF_RESONANCE), 85);
            nil})),
        variation1_pattern[\pat],
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